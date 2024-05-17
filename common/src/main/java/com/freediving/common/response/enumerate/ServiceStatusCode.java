package com.freediving.common.response.enumerate;

import com.fasterxml.jackson.annotation.JsonValue;

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

	OK(200, "OK", false),
	CREATED(201, "CREATED", false),
	NO_CONTENT(204, "NO_CONTENT", false),
	BAD_REQUEST(400, "BAD_REQUEST", true),
	UNAUTHORIZED(401, "UNAUTHORIZED", true),
	FORBIDDEN(403, "FORBIDDEN", true),

	INTERVAL_SERVER_ERROR(500, "INTERVAL_SERVER_ERROR", true)

	// member-service
	, MEMBER_SERVICE(1000, "멤버 서비스 응답 메시지.", false)

	// community-service
	, COMMUNITY_SERVICE(2000, "커뮤니티 서비스 응답 메시지.", false)

	// buddy-service
	, EVENT_TIME_CONFLICT(3409, "이벤트 시간 중복", false);

	//Enum 필드
	private int code;
	private String message;
	private Boolean isErrorType;     // 서비스 비즈니스 Error 타입.

	ServiceStatusCode(int code, String message, Boolean isErrorType) {
		this.code = code;
		this.message = message;
		this.isErrorType = isErrorType;
	}

	@JsonValue
	public int getCode() {
		return code;
	}
}
