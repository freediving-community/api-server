package com.freediving.common.response.enumerate;

import lombok.Getter;

/**
 * 모든 API에서 사용될 API 처리 상태 코드관리 Enum Class
 *  ServiceStatusCode는 HttpStatus가 200인 경우 반환되는 서비스 처리 결과이다.
 *  Http 통신이 200으로 성공했으며, HttpStatus로 표현 불가한 서비스 응답 코드를 내려주기 위함.
 *
 *  버디미 비즈니스 응답 규격 ( 표준 Http 상태코드로 표현 불가능한 비즈니스 코드 )
 *  	- 12XX 코드 : 멤버 관련 서비스 코드
 *  	- 13XX 코드 : 커뮤니티 관련 서비스 코드
 *  	- 14XX 코드 : 버디 이벤트 관련 서비스 코드
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-01-26
 **/
@Getter
public enum ServiceStatusCode {

	OK(200, null, false),
	CREATED(201, null, false),
	NO_CONTENT(204, null, false),
	BAD_REQUEST(400, "BAD_REQUEST", true),

	INTERVAL_SERVER_ERROR(500, "INTERVAL_SERVER_ERROR", true)

	// member-service
	, MEMBER_SERVICE(0, "멤버 서비스 응답 메시지.", false)

	// community-service
	, COMMUNITY_SERVICE(0, "커뮤니티 서비스 응답 메시지.", false)

	// buddy-service
	, BUDDY_SERVICE(0, "버디 서비스 응답 메시지.", false);

	//Enum 필드
	private int code;
	private String message;
	private Boolean isErrorType;     // 서비스 비즈니스 Error 타입.

	ServiceStatusCode(int code, String message, Boolean isErrorType) {
		this.code = code;
		this.message = message;
		this.isErrorType = isErrorType;
	}
}
