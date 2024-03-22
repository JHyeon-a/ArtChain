package com.ssafy.artchain.funding.service;


import com.ssafy.artchain.connectentity.InvestmentLog;
import com.ssafy.artchain.funding.dto.*;
import com.ssafy.artchain.funding.entity.Funding;
import com.ssafy.artchain.funding.entity.FundingAllowStatus;
import com.ssafy.artchain.funding.entity.FundingNotice;
import com.ssafy.artchain.funding.entity.FundingProgressStatus;
import com.ssafy.artchain.funding.repository.FundingNoticeRepository;
import com.ssafy.artchain.funding.repository.FundingRepository;
import com.ssafy.artchain.funding.repository.InvestmentLogRepository;
import com.ssafy.artchain.member.entity.Member;
import com.ssafy.artchain.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FundingServiceImpl implements FundingService {

    private final FundingRepository fundingRepository;
    private final FundingNoticeRepository fundingNoticeRepository;
    private final InvestmentLogRepository investmentLogRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Override
    public int createFunding(FundingCreateRequestDto data) {
        Funding funding = fundingRepository.save(Funding.builder()
                .entId(data.getEntId())
                .name(data.getName())
                .poster(data.getPoster())
                .description(data.getDescription())
                .investmentStructure(data.getInvestmentStructure())
                .estimatedReturn(data.getEstimatedReturn())
                .riskNotice(data.getRiskNotice())
                .progressStatus(FundingProgressStatus.RECRUITMENT_STATUS)
                .goalCoinCount(data.getGoalCoinCount())
                .nowCoinCount(0L)
                .isFinished(false)
                .contractAddress(data.getContractAddress())
                .attachment(data.getAttachment())
                .isAllow(false)
                .category(data.getCategory())
                .recruitEnd(data.getRecruitEnd())
                .investmentLogs(new ArrayList<>())
                .build());

        // -1 리턴 시, 저장이 되지 않은 것
        // 1 리턴 시, 저장이 된 것

        // 저장 여부는 영속화 여부에 따라 확인한다.
        // 정상적으로 저장되었다면 영속화된 상태일 것!
        if (!em.contains(funding)) {
            return -1;
        }
        return 1;
    }

    @Override
    public FundingResponseDto getFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElse(null);

        if (funding != null) {
            return new FundingResponseDto(funding);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public List<FundingResponseDto> getFundingListByCategoryAndStatus(String category, String status, String allowStat, Pageable pageable) {
        String UPPER_ALL = "ALL";
        String RECRUITMENT_END = "RECRUITMENT_END"; // 모집 종료(모집 성공(정산 대기), 모집 실패)

        List<FundingProgressStatus> statuses;
        if (status.toUpperCase(Locale.ROOT).equals(UPPER_ALL) || Stream.of(FundingProgressStatus.values())
                .noneMatch(ps -> ps.name().equals(status))) {
            statuses = List.of(FundingProgressStatus.RECRUITMENT_STATUS,
                    FundingProgressStatus.PENDING_SETTLEMENT, FundingProgressStatus.SETTLED,
                    FundingProgressStatus.RECRUITMENT_FAILED);
        } else if (status.toUpperCase(Locale.ROOT).equals(RECRUITMENT_END)) {
            statuses = List.of(FundingProgressStatus.PENDING_SETTLEMENT,
                    FundingProgressStatus.RECRUITMENT_FAILED);
        } else {
            statuses = List.of(FundingProgressStatus.valueOf(status));
        }

        List<Boolean> allowStatuses;
        if (allowStat.toUpperCase(Locale.ROOT).equals(UPPER_ALL) || Stream.of(FundingAllowStatus.values())
                .noneMatch(als -> als.name().equals(allowStat))) {
            allowStatuses = List.of(true, false);
        } else {
            allowStatuses = List.of(allowStat.toUpperCase(Locale.ROOT).equals("TRUE"));
        }

        Page<Funding> fundingPage;
        if (category.toUpperCase(Locale.ROOT).equals(UPPER_ALL)) {
            fundingPage = fundingRepository.findAllByProgressStatusInAndIsAllowIn(statuses, allowStatuses, pageable);
        } else {
            fundingPage = fundingRepository.findAllByCategoryAndProgressStatusInAndIsAllowIn(category, statuses, allowStatuses, pageable);
        }

        return fundingPage.getContent()
                .stream()
                .map(FundingResponseDto::new)
                .toList();
    }

    @Override
    @Transactional
    public int allowFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElse(null);

        if (funding == null) {
            return -1;
        } else if (funding.getIsAllow()) {
            return 0;
        } else {
            funding.allowFunding(true);
            return 1;
        }
    }

    @Override
    @Transactional
    public int updateFundingProgressStatus(Long fundingId, String progressStatus) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElse(null);

        if (funding == null) {
            return -1;
        }

        if (Stream.of(FundingProgressStatus.values())
                .noneMatch(ps -> ps.name().equals(progressStatus))) {
            return 0;
        }

        funding.updateProgressStatus(FundingProgressStatus.valueOf(progressStatus));
        return 1;
    }

    @Override
    public int createNotice(Long fundingId, FundingNoticeRequestDto dto) {
        Funding funding = fundingRepository.findById(fundingId).orElse(null);
        if (funding == null) {
            return -1;
        }

        FundingNotice fundingNotice = fundingNoticeRepository.save(
                FundingNotice.builder()
                        .funding(funding)
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build()
        );

        // 저장 여부는 영속화 여부에 따라 확인한다.
        // 정상적으로 저장되었다면 영속화된 상태일 것!
        if (!em.contains(fundingNotice)) {
            return 0;
        }
        return 1;
    }

    @Override
    public FundingNoticeResponseDto getFundingNotice(Long fundingId, Long fundingNoticeId) {
        FundingNotice fundingNotice = fundingNoticeRepository.findById(fundingNoticeId)
                .orElse(null);

        if (fundingNotice == null || !Objects.equals(fundingNotice.getFunding().getId(),
                fundingId)) {
            return null;
        }

        return new FundingNoticeResponseDto(fundingNotice);
    }

    @Override
    @Transactional
    public int updateFundingNotice(Long fundingId, Long fundingNoticeId,
                                   FundingNoticeRequestDto dto) {
        FundingNotice fundingNotice = fundingNoticeRepository.findById(fundingNoticeId)
                .orElse(null);

        if ((fundingNotice == null) || !fundingNotice.getFunding().getId().equals(fundingId)) {
            return -1;
        }

        fundingNotice.updateTitleAndContent(dto.getTitle(), dto.getContent());
        return 1;
    }

    @Override
    public int deleteFundingNotice(Long fundingId, Long fundingNoticeId) {
        FundingNotice fundingNotice = fundingNoticeRepository.findById(fundingNoticeId)
                .orElse(null);

        if (fundingNotice == null || !Objects.equals(fundingNotice.getFunding().getId(),
                fundingId)) {
            return -1;
        }

        fundingNoticeRepository.delete(fundingNotice);
        return 1;
    }

    @Override
    @Transactional
    public Long createInvestmentLog(Long fundingId, InvestmentRequestDto dto) {
        // TODO: member 관련은 추후 수정해야 함.
        Member member = memberRepository.findById(17L)
                .orElse(null);
        if (member == null) {
            return -2L;
        }

        Funding funding = fundingRepository.findById(fundingId)
                .orElse(null);
        if (funding == null) {
            return -1L;
        }

        funding.renewNowCoinCount(funding.getNowCoinCount() + dto.getCoinCount());

        InvestmentLog savedInvestmentLog = investmentLogRepository.save(
                InvestmentLog
                        .builder()
                        .member(member)
                        .funding(funding)
                        .transactionHash(dto.getTransactionHash())
                        .transactionTime(dto.getTransactionTime())
                        .coinCount(dto.getCoinCount())
                        .pieceCount(dto.getPieceCount())
                        .build()
        );
        return savedInvestmentLog.getId();
    }
}
