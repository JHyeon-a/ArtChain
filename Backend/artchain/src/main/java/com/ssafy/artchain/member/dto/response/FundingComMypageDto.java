package com.ssafy.artchain.member.dto.response;

import com.ssafy.artchain.funding.entity.FundingProgressStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//기업과 관련된 펀딩 정보를 담는 Dto
public class FundingComMypageDto {
    private Long id;
    private String name;
    private FundingProgressStatus progressStatus;
    private String poster;
    private Long nowCoinCount;
    private Long goalCoinCount;
    private LocalDate recruitEnd;
}