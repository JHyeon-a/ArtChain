package com.ssafy.artchain.member.service;

import com.ssafy.artchain.member.dto.CustomUserDetails;
import com.ssafy.artchain.member.dto.request.CompanyMemberRegistRequestDto;
import com.ssafy.artchain.member.dto.request.MemberRegistRequestDto;
import com.ssafy.artchain.member.dto.request.MemberWalletInfoRequestDto;
import com.ssafy.artchain.member.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    String refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    void companyJoin(CompanyMemberRegistRequestDto companyDto);

    void memberJoin(MemberRegistRequestDto memberDto);

    MemberUserResponseDto getUserInfo(CustomUserDetails customMember);

    MemberUserMypageResponseListDto getUserMypage(CustomUserDetails customMember);

    MemberComMypageResponseDto getComMypage(CustomUserDetails customCompany);
//    MemberComMypageResponseDto getComMypage(CustomUserDetails customCompany) throws Exception ;

    MemberMainUserInfoResponseDto getMainLoginUserInfo(CustomUserDetails member);

    boolean isExistsMemberId(String checkId);

    List<MemberPermissionResponseDto> getComPermissionList();

    void putPermission(Long memberId, String permissionFlag);

    void putMemberWalletInfo(CustomUserDetails member, MemberWalletInfoRequestDto requestDto);

    List<MemberMyTradeDropDownResponseDto> getMyTradeDropDownList(CustomUserDetails customMember);

    List<MemberMyTradeResponseDto> getMyTradeList(CustomUserDetails customMember, Long fundingId, String filterFlag, Pageable pageable) throws Exception;

    void deleteMyTrade(Long marketId);
}
