package com.freediving.authservice.application.port.out;

import com.freediving.authservice.domain.Token;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/25
 * @Description    : Jwt 토큰 생성 및 관리를 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/25        sasca37       최초 생성
 */
public interface CreateTokenPort {

	Token createTokens(String userId, String oauthType);

}
