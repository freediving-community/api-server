package com.freediving.authservice.application.port.out;

import com.freediving.authservice.domain.OauthUser;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : 소셜 별 통신 작업을 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31        sasca37       최초 생성
 */
public interface OauthFeignPort extends OauthRedirectUrlPort {

	OauthUser fetch(String code, String profile);

	String getRedirectUriByProfile(String profile);
}
