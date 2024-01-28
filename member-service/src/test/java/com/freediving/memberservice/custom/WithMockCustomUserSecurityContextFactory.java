package com.freediving.memberservice.custom;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.freediving.memberservice.domain.User;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		User principal = new User(customUser.id(), customUser.oauthType(), customUser.email(),
			customUser.profileImgUrl(), customUser.roleLevel());
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, null);
		context.setAuthentication(authentication);

		return context;
	}
}
