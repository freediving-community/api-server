package com.freediving.authservice.adapter.out.persistence.token;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

import com.freediving.authservice.application.port.out.FindTokenPort;
import com.freediving.authservice.util.JwtTokenUtils;
import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/19
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/19        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class FindTokenPersistenceAdapter implements FindTokenPort {
	private final TokenJpaRepository tokenJpaRepository;

	@Value("${jwt.key}")
	private String key;

	@Override
	public String findRefreshTokenByUserId(Long userId) {
		String userIdStr = String.valueOf(userId);
		Optional<TokenJpaEntity> token = tokenJpaRepository.findByUserId(String.valueOf(userIdStr));
		if (token.isPresent()) {
			String oauthType = JwtTokenUtils.extractOauthType(token.get().getRefreshToken(), key);
			return JwtTokenUtils.generateAccessToken(userIdStr, oauthType, key);
		}
		throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "유저 정보가 유효하지 않습니다.");
	}
}
