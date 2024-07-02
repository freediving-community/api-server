package com.freediving.communityservice.adapter.in.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckBoardRoleInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		Map<String, String> pathVariables = (Map<String, String>)request.getAttribute(
			HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String boardType = pathVariables.get("boardType");

		BoardType type = null;
		try {
			type = BoardType.valueOf(boardType.toUpperCase());
		} catch (IllegalArgumentException e) {
			// 매핑되지 않는 boardType인 경우 처리
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return false;
		}

		return true;
	}
}
