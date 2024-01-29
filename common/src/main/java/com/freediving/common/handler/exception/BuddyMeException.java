package com.freediving.common.handler.exception;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

public class BuddyMeException extends RuntimeException {

	private final ServiceStatusCode serviceStatusCode;
	private final ResponseJsonObject responseJsonObject;

	public ResponseJsonObject getResponseJsonObject() {
		return responseJsonObject;
	}

	public BuddyMeException(ServiceStatusCode apiStatusCode) {
		this.serviceStatusCode = apiStatusCode;
		responseJsonObject = ResponseJsonObject.builder().code(serviceStatusCode).build();
	}

	public BuddyMeException(ServiceStatusCode apiStatusCode, String msg) {
		super(msg);
		this.serviceStatusCode = apiStatusCode;
		responseJsonObject = ResponseJsonObject.builder().code(serviceStatusCode).expandMsg(msg).build();
	}
}
