package com.freediving.authservice.application.port.out.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.freediving.authservice.domain.OauthUser;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : MemberService에 통신 작업 매핑
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */

@FeignClient(name = "MEMBER-SERVICE")
public interface MemberServiceFeignClient {

	@PostMapping(path = "/v1/service/users/register")
	OauthUser postOauthUserInfo(OauthUser oauthUser);
}
