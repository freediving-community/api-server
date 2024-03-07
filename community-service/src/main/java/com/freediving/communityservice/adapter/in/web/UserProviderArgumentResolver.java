package com.freediving.communityservice.adapter.in.web;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.freediving.communityservice.adapter.in.web.UserProvider.RoleLevel;

import lombok.RequiredArgsConstructor;

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
		String userId = webRequest.getHeader("UserId");
		String roleLevel = webRequest.getHeader("RoleLevel");
		UserProvider currentUserProvider = userProvider.getIfAvailable();

		if (userId == null || roleLevel == null) {
			currentUserProvider.setRequestUserId(-1L);
			currentUserProvider.setRoleLevel(RoleLevel.ANONYMOUS);
			// return new UserProvider(-1L, RoleLevel.ANONYMOUS);
		} else {
			RoleLevel rl = RoleLevel.valueOf(Integer.parseInt(roleLevel));
			currentUserProvider.setRequestUserId(Long.valueOf(userId));
			currentUserProvider.setRoleLevel(rl);
		}
        return currentUserProvider;
		// RoleLevel rl = RoleLevel.valueOf(Integer.parseInt(roleLevel));
		// return new UserProvider(Long.valueOf(userId), rl);
	}
}
