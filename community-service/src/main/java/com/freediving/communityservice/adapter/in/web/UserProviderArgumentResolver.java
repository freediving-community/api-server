package com.freediving.communityservice.adapter.in.web;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.freediving.communityservice.adapter.in.web.UserProvider.RoleLevel;

@Component
public class UserProviderArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserProvider.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String userId = webRequest.getHeader("UserId");
		String roleLevel = webRequest.getHeader("RoleLevel");
		if (userId == null || roleLevel == null) {
			return new UserProvider(-1L, RoleLevel.ANONYMOUS);
		}
		RoleLevel rl = RoleLevel.valueOf(Integer.parseInt(roleLevel));
		return new UserProvider(Long.valueOf(userId), rl);
	}
}
