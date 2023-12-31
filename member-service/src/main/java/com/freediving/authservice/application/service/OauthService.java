package com.freediving.authservice.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.authservice.adapter.in.web.UserLoginResponse;
import com.freediving.authservice.application.port.in.JwtTokenUseCase;
import com.freediving.authservice.application.port.in.JwtTokenUtils;
import com.freediving.authservice.application.port.in.OauthUseCase;
import com.freediving.authservice.application.port.out.OauthTemplate;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OauthService implements OauthUseCase, JwtTokenUseCase {

	@Value("${jwt.key}")
	private String key;

	private final OauthTemplate oauthTemplate;

	@Override
	public String provideOauthType(OauthType oauthType) {
		return oauthTemplate.doRequest(oauthType);
	}

	@Override
	public UserLoginResponse login(OauthType oauthType, String code) {
		OauthUser oauthUser = oauthTemplate.doPostTokenAndGetInfo(oauthType, code);
		UserLoginResponse userLoginResponse = UserLoginResponse.from(oauthUser);
		// JWT Token 생성
		provideJwtToken(userLoginResponse);
		return userLoginResponse;
	}

	@Override
	public void provideJwtToken(UserLoginResponse userLoginResponse) {
		OauthType oauthType = userLoginResponse.getOauthType();
		String email = userLoginResponse.getEmail();
		String accessToken = JwtTokenUtils.generateAccessToken(oauthType, email, key);
		String refreshToken = JwtTokenUtils.generateRefreshToken(oauthType, email, key);
		userLoginResponse.updateTokens(accessToken, refreshToken);
	}
}
