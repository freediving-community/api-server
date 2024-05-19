package com.freediving.authservice.adapter.out.persistence.token;

import java.util.Optional;

import com.freediving.authservice.application.port.out.FindTokenPort;
import com.freediving.common.config.annotation.PersistenceAdapter;

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

	@Override
	public String findRefreshTokenByUserId(Long userId) {
		Optional<TokenJpaEntity> token = tokenJpaRepository.findByUserId(String.valueOf(userId));
		if (token.isPresent()) {
			return token.get().getRefreshToken();
		}
		return null;
	}
}
