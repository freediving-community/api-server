/*
package com.freediving.communityservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultRestControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		// TODO common 코드 정의 필요. API Exception 900 / Internal Server Error 500 / BadRequest 400 등

		if (e instanceof IllegalArgumentException) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.internalServerError().body(e.getMessage());
	}

	// static class ResponseEntityException

}
*/
