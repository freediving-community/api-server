package com.freediving.memberservice.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@AllArgsConstructor
@Getter
public enum ErrorCode {

	// 400 - BAD REQUEST
	INVALID_LICENCE_LEVEL(HttpStatus.BAD_REQUEST, "자격증 레벨 정보가 없습니다."),

	// 401 - UNAUTHORIZED
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "기간이 만료된 토큰 입니다."),

	// 404 - NOT FOUND
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저 정보가 없습니다.");

	private HttpStatus status;
	private String message;
}
