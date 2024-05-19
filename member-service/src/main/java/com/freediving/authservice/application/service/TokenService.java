package com.freediving.authservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.authservice.application.port.in.TokenUseCase;
import com.freediving.authservice.application.port.out.CreateTokenPort;
import com.freediving.authservice.application.port.out.FindTokenPort;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/04/20
 * @Description    : Token 관리 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/04/20        sasca37       최초 생성
 */

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TokenService implements TokenUseCase {

	private final CreateTokenPort createTokenPort;
	private final FindTokenPort findTokenPort;

	@Override
	public String updateTokens(Long userId, String oauthTypeName) {
		return createTokenPort.updateTokens(String.valueOf(userId), oauthTypeName);
	}

	@Override
	public String findRefreshTokenByUserId(Long userId) {
		return findTokenPort.findRefreshTokenByUserId(userId);
	}
}
