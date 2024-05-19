package com.freediving.authservice.application.port.in;

import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/04/20
 * @Description    : Token 정보를 관리하는 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/04/20        sasca37       최초 생성
 */

@UseCase
public interface TokenUseCase {

	String updateTokens(Long userId, String oauthTypeName);

	String findRefreshTokenByUserId(Long userId);
}
