package org.freediving.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/08
 * @Description    : Gateway Exception Handler
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/08        sasca37       최초 생성
 */

@Slf4j
@RestControllerAdvice
public class GatewayExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<GatewayException> handleException(RuntimeException ex) {
		log.error("RuntimeExceptionHandler : {} \n StackTrace : ", ex.getMessage(), ex.getStackTrace());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "{}", ex.getMessage()));
	}
}
