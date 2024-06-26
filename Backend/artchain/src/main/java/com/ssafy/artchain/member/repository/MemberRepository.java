package com.ssafy.artchain.member.repository;

import com.ssafy.artchain.member.dto.response.FundingComMypageDto;
import com.ssafy.artchain.member.dto.response.MemberUserMypageResponseDto;
import com.ssafy.artchain.member.entity.Member;
import com.ssafy.artchain.member.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);

    List<Member> findAllByPermissionAndAuthority(Permission permission, String authority);

    boolean existsByMemberId(String memberId);

    @Query(value =
            "select new com.ssafy.artchain.member.dto.response.MemberUserMypageResponseDto(m.id, m.name, m.walletAddress, m.walletBalance, f.progressStatus, count(f.progressStatus))" +
                    "from Member as m left join InvestmentLog i on m = i.member" +
                    " left join Funding f on i.funding = f" +
                    " where m.id = :memberId " +
                    "group by m.id, m.name, m.walletAddress, m.walletBalance, f.progressStatus")
    List<MemberUserMypageResponseDto> memberMypage(
            @Param("memberId") Long memberId
    );


    @Query(value = "select new com.ssafy.artchain.member.dto.response.FundingComMypageDto(f.id, f.name, f.progressStatus, f.poster, f.goalCoinCount, f.nowCoinCount, f.recruitEnd, s.status, f.contractAddress)" +
            "from Member as m " +
            " left join Funding f on m.id = f.entId" +
            " left join Settlement s on m.id = s.entId and f.id = s.fundingId" +
            " where m.id = :companyId ")
    List<FundingComMypageDto> comMypage(@Param("companyId") Long companyId);

}
