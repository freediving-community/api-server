package com.freediving.communityservice.adapter.in.web;

import java.util.Optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.AuditorAware;

public class UserProviderAuditorAware implements AuditorAware<Long> {
	private final ObjectProvider<UserProvider> userProvider;

	public UserProviderAuditorAware(ObjectProvider<UserProvider> userProvider) {
		this.userProvider = userProvider;
	}

	@Override
	public Optional<Long> getCurrentAuditor() {
		return Optional.ofNullable(userProvider.getIfAvailable().getRequestUserId());
	}
}
