package com.freediving.buddyservice.common;

import org.springframework.context.annotation.Import;

import com.freediving.common.handler.exception.ServiceExceptionHandler;

@Import(ServiceExceptionHandler.class)
public class ControllerDefendenciesConfig {
}
