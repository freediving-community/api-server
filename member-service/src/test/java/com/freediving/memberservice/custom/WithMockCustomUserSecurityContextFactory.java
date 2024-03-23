package com.freediving.memberservice.custom;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicence;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		UserLicence userLicence = new UserLicence(customUser.licenceLevel(), customUser.licenceImgUrl(),
			customUser.licenceValidTF(), customUser.confirmAdminId());
		User principal = new User(customUser.id(), customUser.email(), customUser.profileImgUrl(),
			customUser.nickname(), customUser.content(), customUser.oauthType(), customUser.roleLevel(), userLicence);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, null);
		context.setAuthentication(authentication);

		return context;
	}
}
