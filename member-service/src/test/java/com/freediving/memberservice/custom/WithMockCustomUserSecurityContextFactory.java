package com.freediving.memberservice.custom;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicense;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		UserLicense userLicense = new UserLicense(customUser.diveType(), null, customUser.roleLevel(),
			customUser.licenseLevel(), customUser.licenseImgUrl(), customUser.licenseValidTF(),
			customUser.confirmAdminId());
		List<UserLicense> userLicenseList = Arrays.asList(userLicense);

		User principal = new User(customUser.id(), null, customUser.email(), customUser.profileImgUrl(),
			customUser.nickname(), customUser.content(), customUser.oauthType(), userLicenseList, null, null);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, null);
		context.setAuthentication(authentication);

		return context;
	}
}
