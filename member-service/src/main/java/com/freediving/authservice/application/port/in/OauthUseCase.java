package com.freediving.authservice.application.port.in;

import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : Oauth 정보 제공 및 로그인 기능을 수행하는 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */

@UseCase
public interface OauthUseCase {

	String provideOauthType(OauthType oauthType, String profile);

	OauthUser login(OauthType oauthType, String code, String profile);
}
