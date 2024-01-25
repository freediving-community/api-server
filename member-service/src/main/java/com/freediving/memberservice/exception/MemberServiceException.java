package com.freediving.memberservice.exception;

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

@Getter
@AllArgsConstructor
public class MemberServiceException extends RuntimeException {
	private ErrorCode errorCode;
	private String message;

	public MemberServiceException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
}
