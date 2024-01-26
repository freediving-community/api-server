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
public class ResponseJsonObject {

	@Builder.Default
	private ServiceStatusCode code = ServiceStatusCode.OK;
	@Builder.Default
	private String expandMsg = null;
	private Object data;

	// --------------- JSON 필드 --------------------
	public int getCode() {
		return this.code.getCode();
	}

	public String getMsg() {
		// 추가 메시지가 있는 경우
		if (expandMsg != null)
			return String.format("{0}({1)", this.code.getMessage(), expandMsg);

		return this.code.getMessage();
	}

	// data
	public Object getData() {
		return data;
	}
	// ---------------------------------------------

	public Boolean errorType() {
		return code.getIsErrorType();
	}
}
