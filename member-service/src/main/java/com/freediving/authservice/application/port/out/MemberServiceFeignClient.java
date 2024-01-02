package com.freediving.authservice.application.port.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.freediving.authservice.domain.OauthUser;

@FeignClient(name = "MEMBER-SERVICE")
public interface MemberServiceFeignClient {

	@PostMapping(path = "/v1/users/register")
	void postOauthUserInfo(OauthUser oauthUser);
}
