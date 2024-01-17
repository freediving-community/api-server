package com.freediving.authservice.adapter.out.service;

import com.freediving.authservice.application.port.out.service.MemberFeignPort;
import com.freediving.authservice.application.port.out.service.MemberServiceFeignClient;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.ExternalSystemAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : MemberService 와 통신 작업을 하는 Adapter
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@ExternalSystemAdapter
@RequiredArgsConstructor
@Slf4j
public class MemberExternalAdapter implements MemberFeignPort {

	private final MemberServiceFeignClient memberServiceFeignClient;

	@Override
	public OauthUser createOrUpdateUserRequest(OauthUser oauthUser) {
		return memberServiceFeignClient.postOauthUserInfo(oauthUser);
	}
}
