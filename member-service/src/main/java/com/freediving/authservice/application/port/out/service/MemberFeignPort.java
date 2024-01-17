package com.freediving.authservice.application.port.out.service;

import com.freediving.authservice.domain.OauthUser;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : MemberService와 Feign 통신을 하는 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */
public interface MemberFeignPort {

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/18
	 * @Param            : OauthUser (AuthService)
	 * @Return           : OauthUser (MemberService)
	 * @Description      : AuthService 내 유저 정보를 바탕으로 MemberService에 신규 가입 / JWT 저장 작업
	 */
	OauthUser createOrUpdateUserRequest(OauthUser oauthUser);
}
