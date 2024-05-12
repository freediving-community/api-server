package com.freediving.common.response;

import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 버디미 서비스 API의 공통 응답 규격.
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-01-26
 **/
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseJsonObject<T> {

	@Builder.Default
	private ServiceStatusCode code = ServiceStatusCode.OK;
	@Builder.Default
	private String expandMsg = null; //todo 추후 삭제 필드  msg 필드 사용해주세요.
	private String msg = null;
	private T data;

	public ResponseJsonObject(ServiceStatusCode serviceStatusCode, T data) {
		this.code = serviceStatusCode;
		this.data = data;
	}

	public ResponseJsonObject(ServiceStatusCode serviceStatusCode, T data, String msg) {
		this.code = serviceStatusCode;
		this.data = data;
		this.expandMsg = msg;
		this.msg = msg;
	}

	// --------------- JSON 필드 --------------------
	public int getCode() {
		return this.code.getCode();
	}

	public String getMsg() {
		// 추가 메시지가 있는 경우
		if (this.expandMsg != null)
			return String.format("%s(%s)", this.code.getMessage(), this.expandMsg);

		return this.code.getMessage();
	}

	// data
	public T getData() {
		return this.data;
	}
	// ---------------------------------------------

	public Boolean errorType() {
		return this.code.getIsErrorType();
	}
}
