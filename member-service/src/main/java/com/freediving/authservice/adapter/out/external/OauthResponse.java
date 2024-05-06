package com.freediving.authservice.adapter.out.external;

import com.freediving.authservice.domain.OauthType;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : 소셜 별 로그인 응답 정보에서 필요한 필드를 저장
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */
public record OauthResponse(OauthType oauthType, String email, String profileImgUrl, String providerId) {

	public static OauthResponse of(OauthType oauthType, String email, String profileImgUrl, String providerId) {
		return new OauthResponse(oauthType, email, profileImgUrl, providerId);
	}
}
