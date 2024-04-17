package com.freediving.buddyservice.config.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Filter initialization logic
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String userId = httpRequest.getHeader("User-Id");

		if (userId != null) {
			httpRequest.setAttribute("User-Id", userId);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Logic needed when filter is destroyed
	}
}