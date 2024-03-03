package com.freediving.authservice.adapter.out.persistence.token;

import org.springframework.beans.factory.annotation.Value;

import com.freediving.authservice.application.port.out.CreateTokenPort;
import com.freediving.authservice.domain.Token;
import com.freediving.authservice.util.JwtTokenUtils;
import com.freediving.common.config.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 사용자의 액세스 토큰을 생성하여 저장 및 관리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateTokenPersistenceAdapter implements CreateTokenPort {
	private final TokenJpaRepository tokenJpaRepository;

	@Value("${jwt.key}")
	private String key;

	@Override
	public Token createTokens(String userId, String oauthType) {
		String accessToken = JwtTokenUtils.generateAccessToken(userId, oauthType, key);
		String refreshToken = JwtTokenUtils.generateRefreshToken(userId, oauthType, key);

		Token token = Token.createToken(accessToken, refreshToken);
		TokenJpaEntity tokenJpaEntity = TokenJpaEntity.createToken(token.refreshToken());
		tokenJpaRepository.save(tokenJpaEntity);

		return token;
	}
}
