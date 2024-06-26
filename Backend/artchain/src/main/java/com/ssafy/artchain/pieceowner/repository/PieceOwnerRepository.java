package com.ssafy.artchain.pieceowner.repository;

import com.ssafy.artchain.funding.entity.FundingProgressStatus;
import com.ssafy.artchain.market.dto.MarketRegistFundingNameResponseDto;
import com.ssafy.artchain.pieceowner.dto.PieceOwnerResponseDto;
import com.ssafy.artchain.pieceowner.entity.PieceOwner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieceOwnerRepository extends JpaRepository<PieceOwner, Long> {

    PieceOwner findPieceOwnerByMemberIdAndFundingId(Long memberId, Long fundingId);

    @Query(value = "select " +
            "new com.ssafy.artchain.pieceowner.dto.PieceOwnerResponseDto ( " +
            "po.id, " +
            "mb.id," +
            "mb.name," +
            "fd.id," +
            "fd.name," +
            "po.pieceCount ) " +
            "from PieceOwner po " +
            "join Member mb on po.memberId = mb.id " +
            "join Funding fd on po.fundingId = fd.id " +
            "where po.memberId = :memberId " +
            "and fd.progressStatus = :fundingProgressStatus " +
            "order by po.pieceCount desc"
    )
    List<PieceOwnerResponseDto> findTop3ByMemberIdAndFundingProgressStatus(@Param("memberId") Long memberId,
                                                                           @Param("fundingProgressStatus") FundingProgressStatus fundingProgressStatus,
                                                                           Pageable pageable);

    @Query(value = "select SUM(po.pieceCount) " +
            "from PieceOwner po " +
            "join Member mb on po.memberId = mb.id " +
            "join Funding fd on po.fundingId = fd.id " +
            "where po.memberId = :memberId " +
            "and fd.progressStatus = :fundingProgressStatus " +
            "and fd.id NOT IN :excludedFundingIdList")
    Long sumPieceCountByMemberIdAndFundingProgressStatusExcludingFundingIds(
            @Param("memberId") Long memberId,
            @Param("fundingProgressStatus") FundingProgressStatus fundingProgressStatus,
            @Param("excludedFundingIdList") List<Long> excludedFundingIdList);


    @Query(value = "select " +
            "new com.ssafy.artchain.pieceowner.dto.PieceOwnerResponseDto ( " +
            "po.id, " +
            "mb.id," +
            "mb.name," +
            "fd.id," +
            "fd.name," +
            "po.pieceCount ) " +
            "from PieceOwner po " +
            "join Member mb on po.memberId = mb.id " +
            "join Funding fd on po.fundingId = fd.id " +
            "where po.memberId = :memberId " +
            "and fd.progressStatus = :fundingProgressStatus "
    )
    List<PieceOwnerResponseDto> findAllByMemberIdAndFundingProgressStatus(@Param("memberId") Long memberId,
                                                                          @Param("fundingProgressStatus") FundingProgressStatus fundingProgressStatus);

    @Query("select " +
            "new com.ssafy.artchain.market.dto.MarketRegistFundingNameResponseDto (" +
            "po.id, " +
            "fd.id, " +
            "fd.name, " +
            "po.pieceCount, " +
            "fd.contractAddress ) " +
            "from PieceOwner po " +
            "join Funding fd on po.fundingId = fd.id " +
            "where po.memberId = :memberId " +
            "and fd.progressStatus = :fundingProgressStatus")
    List<MarketRegistFundingNameResponseDto> findMarketRegistFundingNameResponseDto(@Param("memberId") Long memberId,
                                                                                    @Param("fundingProgressStatus") FundingProgressStatus fundingProgressStatus);

    List<PieceOwner> findAllByFundingId(Long fundingId);
}
