package com.ssafy.polaris.book.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

	// 200 OK : 성공
	SUCCESS_NEW_ACCESS_TOKEN(200, "Access 토큰 재발급 성공"),
	SUCCESS_NEW_NORMAL_USER(200, "일반 유저 등록 성공"),
	SUCCESS_NEW_COMPANY_USER(200, "회사 유저 등록 성공"),
	SUCCESS_USER_VIEW(200, "일반 유저 조회 성공"),
	SUCCESS_COMPANY_VIEW(200, "회사 유저 조회 성공"),
	SUCCESS_USER_MAIN_VIEW(200, "메인 화면 유저 정보 조회 성공"),
	SUCCESS_USER_MEMBERID_CHECK(200, "사용 가능한 아이디입니다."),
	SUCCESS_PERMISSION_COMPANYS_VIEW(200, "승인 대기중인 기업 목록 조회 성공"),
	SUCCESS_PERMISSION_COMPANY_PUT(200, "승인 대기중인 기업 처리 성공"),
	SUCCESS_USER_WALLET_PUT(200,"지갑 정보 등록 성공"),
	SUCCESS_MYTRADE_DROPDOWN_VIEW(200,"나의 거래 드롭다운 메뉴 조회 성공"),
	SUCCESS_MYTRADE_LIST_VIEW(200,"나의 거래 리스트 조회 성공"),
	SUCCESS_MYTRADE_DELETE(200, "성공적으로 거래를 비활성화함"),
	// 201 CREATED : 새로운 리소스 생성
//	SUCCESS_CREATE_USER_BOOK(201, "사용자 도서 생성 성공"),

	// 204 NO CONTENT : 성공하였으나, 반환할 값이 없음
//	NO_CONTENT_IN_LIBRARY_VIEW(204, "서재에 도서가 없음"),
	NO_CONTENT_IN_FUNDING_MYPAGE_VIEW(204, "관련된 펀딩 정보가 없음"),

	// 400 BAD REQUEST : 잘못된 요청 - 요청 구문이 잘못되었음
//	FAIL_USER_BOOK_DELETE(400, "잘못된 요청으로 인한 사용자 도서 삭제 실패"),
	FAIL_ALREADY_EXIST_MEMBERID(400, "이미 존재하는 사용자 아이디입니다."),
	FAIL_NOT_ALLOW(400, "해당 요청에 필요한 권한이 없습니다."),
	FAIL_WRONG_FILTER_FLAG(400, "잘못된 filterFlag입니다."),

	// 404 NOT FOUND
	FAIL_NEW_ACCESS_TOKEN(404, "Access 토큰 재발급 실패");

	private final int status;
	private final String message;
}