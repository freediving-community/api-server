package com.freediving.communityservice.adapter.in.web;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserProviderArgumentResolver implements HandlerMethodArgumentResolver {

	private final ObjectProvider<UserProvider> userProvider;

	@Autowired
	public UserProviderArgumentResolver(ObjectProvider<UserProvider> userProvider) {
		this.userProvider = userProvider;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserProvider.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String userId = webRequest.getHeader("User-Id");
		// String roleLevel = webRequest.getHeader("RoleLevel");
		UserProvider currentUserProvider = userProvider.getObject();

		if (userId == null/* || roleLevel == null*/) {
			currentUserProvider.setRequestUserId(-1L);
			// currentUserProvider.setRoleLevel(RoleLevel.ANONYMOUS);
		} else {
			// RoleLevel rl = RoleLevel.valueOf(Integer.parseInt(roleLevel));
			currentUserProvider.setRequestUserId(Long.valueOf(userId));
			// currentUserProvider.setRoleLevel(rl);
		}
		return currentUserProvider;
	}
}
