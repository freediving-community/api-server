package com.freediving.common.handler.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * 버디미 서비스 RestController에서 발생하는 API Exception을 핸들링 한다.
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-01-29
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.freediving")
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * RuntimeException 핸들링.
	 *
	 * @param ex Exception
	 * @return desc
	 */
	@ApiResponse(
		responseCode = "500",
		description = "시스템 에러",
		content = @Content(
			mediaType = "application/json"
		)
	)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseJsonObject> handleException(Exception ex) {
		log.error("RuntimeExceptionHandler : {} \n StackTrace : ", ex.getMessage(), ex.getStackTrace());

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Bean Validation API 사용하여 @Valid, @Validated 어노테이션 사용시 파라미터 유효성 검증 실패시 발생하는
	 * ConstraintViolationException 대해서 핸들링 한다.
	 *
	 * @param ex ConstraintViolationException
	 * @return desc
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseJsonObject> handleConstraintViolationException(ConstraintViolationException ex) {
		log.error("파라미터 유효성 체크 실패. : {} \n StackTrace : ", ex.getMessage(), ex.getStackTrace());

		ResponseJsonObject response;

		if (ex.getConstraintViolations().isEmpty() == false) {
			String exceptionMsg = ex.getConstraintViolations().stream()
				.map(v1 -> v1.getMessage())
				.collect(Collectors.joining(","));
			response = ResponseJsonObject.builder()
				.code(ServiceStatusCode.BAD_REQUEST)
				.expandMsg(exceptionMsg)
				.build();
		} else
			response = ResponseJsonObject.builder().code(ServiceStatusCode.BAD_REQUEST).build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @RequestBody의 매핑 실패일경우  MethodArgumentNotValidException 예외 에러가 발생한다.
	 *
	 * @param ex MethodArgumentNotValidException
	 * @return desc
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.error("파라미터 유효성 체크 실패. : {} \n StackTrace : ", ex.getMessage(), ex.getStackTrace());
		ResponseJsonObject response;

		if (ex.getBindingResult().hasErrors())
			response = ResponseJsonObject.builder().code(ServiceStatusCode.BAD_REQUEST)
				.expandMsg(ex.getBindingResult().getFieldError().getDefaultMessage()).build();
		else
			response = ResponseJsonObject.builder().code(ServiceStatusCode.BAD_REQUEST).build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
		HttpStatusCode status, WebRequest request) {
		log.error("파라미터 타입 변환 실패. : {} \n StackTrace : ", ex.getMessage(), ex.getStackTrace());
		ResponseJsonObject response;

		response = ResponseJsonObject.builder().code(ServiceStatusCode.BAD_REQUEST)
			.expandMsg("요청된 Json 파싱 오류. 잘못된 타입의 데이터로 요청되었습니다.").build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BuddyMeException.class)
	public ResponseEntity<ResponseJsonObject> handleDikeServiceException(BuddyMeException ex) {
		log.debug("DikeServiceExceptionHandler : {}", ex.getResponseJsonObject().toString());
		HttpStatus exceptionStatus = HttpStatus.OK;

		switch (ex.getResponseJsonObject().getCode()) {
			case 201:
				exceptionStatus = HttpStatus.CREATED;
				break;
			case 204:
				exceptionStatus = HttpStatus.NO_CONTENT;
				break;
			case 400:
				exceptionStatus = HttpStatus.BAD_REQUEST;
				break;
			case 401:
				exceptionStatus = HttpStatus.UNAUTHORIZED;
				break;
			default:
				exceptionStatus = HttpStatus.OK;
		}
		if (ex.getResponseJsonObject().errorType() == true)
			log.error(ex.getMessage());

		return new ResponseEntity<>(ex.getResponseJsonObject(), exceptionStatus);
	}
}
