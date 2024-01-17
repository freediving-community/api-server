package com.freediving.authservice.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.authservice.application.port.in.JwtTokenUseCase;
import com.freediving.authservice.application.port.in.OauthUseCase;
import com.freediving.authservice.application.port.out.OauthTemplate;
import com.freediving.authservice.application.port.out.service.MemberFeignPort;
import com.freediving.authservice.application.port.out.service.MemberUseCase;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : 소셜 별 로그인 작업을 진행하고 MemberService에 유저 정보를 전달하는 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OauthService implements OauthUseCase, JwtTokenUseCase, MemberUseCase {

	@Value("${jwt.key}")
	private String key;

	private final OauthTemplate oauthTemplate;
	private final MemberFeignPort memberFeignPort;

	@Override
	public String provideOauthType(OauthType oauthType) {
		return oauthTemplate.doRequest(oauthType);
	}

	@Override
	public OauthUser login(OauthType oauthType, String code) {
		// 소셜 로그인
		OauthUser oauthUser = oauthTemplate.doPostTokenAndGetInfo(oauthType, code);

		// JWT 토큰 생성
		provideJwtToken(oauthUser, key);

		// MemberService 에 소셜 정보를 가진 OauthUser를 전달 및 User 정보 반환
		OauthUser user = createOrFindUserRequest(oauthUser);
		user.updateAccessToken(oauthUser.getAccessToken());
		return user;
	}

	@Override
	public OauthUser createOrFindUserRequest(OauthUser oauthUser) {
		return memberFeignPort.createOrUpdateUserRequest(oauthUser);
	}
}
