package com.ssafy.artchain.coin.controller;

import com.ssafy.artchain.coin.defaultResponse.DefaultResponse;
import com.ssafy.artchain.coin.dto.request.CoinRegistRequestDto;
import com.ssafy.artchain.coin.dto.response.CoinHistoryResponseDto;
import com.ssafy.artchain.coin.dto.response.CoinMainResponseDto;
import com.ssafy.artchain.coin.entity.InoutFlag;
import com.ssafy.artchain.coin.service.CoinServiceImpl;
import com.ssafy.artchain.member.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssafy.artchain.coin.defaultResponse.StatusCode.*;

@RestController
@RequestMapping("/api/coin")
@RequiredArgsConstructor
@Slf4j
public class CoinController {
/*
    Todo:환전하기 메인화면 Get #Clear
    Todo:충전한 내역 보내주기
    Todo: 충전하기( Member-walletBalance 올려주고, 내역 받아서 저장 ) #Clear
    Todo: 환전하기( Member-walletBalance에 환전할 만큼 있는지 확인 후 차감해주고, 내역 받아서 저장 )
    Todo: 환전한 내역 보내주기

     */

    private final CoinServiceImpl coinService;

    @GetMapping
    public ResponseEntity<DefaultResponse<CoinMainResponseDto>> getCoinMain(@AuthenticationPrincipal CustomUserDetails member){
        CoinMainResponseDto dto = coinService.getCoinMain(member);
        return DefaultResponse.toResponseEntity(HttpStatus.OK,SUCCESS_COIN_MAIN_VIEW,dto);
    }

    @GetMapping("/history")
    public ResponseEntity<DefaultResponse<List<CoinHistoryResponseDto>>> getCoinHistoryList(@AuthenticationPrincipal CustomUserDetails member, @RequestParam String inoutFlag, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<CoinHistoryResponseDto> list = coinService.getCoinChargeList(member, inoutFlag, pageable);
        return DefaultResponse.toResponseEntity(HttpStatus.OK,SUCCESS_COIN_MAIN_VIEW,list);
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<CoinRegistRequestDto>> postCoinCharge(@AuthenticationPrincipal CustomUserDetails member, @RequestBody CoinRegistRequestDto coinRegistRequestDto){
        try {
            coinService.postCoinCharge(member, coinRegistRequestDto);
            if(coinRegistRequestDto.getInoutFlag().equals(InoutFlag.충전)){
                return DefaultResponse.emptyResponse(HttpStatus.OK,SUCCESS_CREATE_COIN_CHARGE);
            }
            else {
                return DefaultResponse.emptyResponse(HttpStatus.OK,SUCCESS_CREATE_COIN_EXCHANGE);
            }

        } catch (Exception e){
            log.debug(e.getMessage());
            return DefaultResponse.emptyResponse(HttpStatus.OK, FAIL_CREATE_COIN_HISTORY);
        }

    }

}
