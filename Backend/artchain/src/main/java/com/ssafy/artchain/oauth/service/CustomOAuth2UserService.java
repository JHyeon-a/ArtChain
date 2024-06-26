package com.ssafy.artchain.oauth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.artchain.member.entity.Member;
import com.ssafy.artchain.member.repository.MemberRepository;
import com.ssafy.artchain.oauth.dto.CustomOAuth2User;
import com.ssafy.artchain.oauth.dto.KakaoResponse;
import com.ssafy.artchain.oauth.dto.MemberDto;
import com.ssafy.artchain.oauth.dto.OAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String oauthClientName = userRequest.getClientRegistration().getClientName();
        try {
            log.info(new ObjectMapper().writeValueAsString(oAuth2User.getAuthorities()));

        } catch (Exception e) {
            e.printStackTrace();
        }
//    Member member = null;
//    String memberId = null;
        OAuth2Response oAuth2Response = null;
        if (oauthClientName.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }
//    oAuth2Response 완료
        String kakaoId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        boolean existMember = memberRepository.existsByMemberId(kakaoId);
//    DB에 저 멤버 정보가 없다면
        if (!existMember) {
            log.info("DB에 멤버 정보가 없습니다");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedPw = bCryptPasswordEncoder.encode(pw);
            Member member = Member.builder()
                    .memberId(kakaoId)
                    .password(hashedPw)
                    .authority("ROLE_USER")
                    .name(oAuth2Response.getName())
                    .isDeleted(false)
                    .build();

            memberRepository.save(member);

            MemberDto memberDto = new MemberDto();
            memberDto.setId(kakaoId);
            memberDto.setNickName(oAuth2Response.getName());
            memberDto.setAuthority("ROLE_USER");

            return new CustomOAuth2User(memberDto);
        } else {
            log.info("DB에 멤버 정보가 있습니다");
            Member member = memberRepository.findByMemberId(kakaoId)
                    .orElseThrow(() -> new NoSuchElementException("MEMBER NOT FOUND"));

            member.updateName(oAuth2Response.getName());

            memberRepository.save(member);

            MemberDto memberDto = new MemberDto();
            memberDto.setId(member.getMemberId());
            memberDto.setNickName(oAuth2Response.getName());
            memberDto.setAuthority(member.getAuthority());

            return new CustomOAuth2User(memberDto);
        }
    }
}
