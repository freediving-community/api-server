package com.freediving.authservice.application.port.in;

import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : Jwt 토큰 생성 작업을 위한 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */

@UseCase
public interface JwtTokenUseCase {

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/18
	 * @Param            : OauthUser, key
	 * @Return           : void
	 * @Description      : 파라미터로 전달 받은 유저 정보를 통해 Jwt 토큰 생성 및 업데이트
	 */
	default void provideJwtToken(OauthUser oauthUser, String key) {
		OauthType oauthType = oauthUser.getOauthType();
		String email = oauthUser.getEmail();
		String accessToken = JwtTokenUtils.generateAccessToken(oauthType, email, key);
		String refreshToken = JwtTokenUtils.generateRefreshToken(oauthType, email, key);
		oauthUser.updateTokens(accessToken, refreshToken);
	}
}
