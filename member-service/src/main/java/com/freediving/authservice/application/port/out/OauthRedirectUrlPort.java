package com.freediving.authservice.application.port.out;

import com.freediving.authservice.domain.OauthType;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : 소셜 로그인 팝업 생성 URL을 가져오기 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31        sasca37       최초 생성
 */
public interface OauthRedirectUrlPort {

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            :
	 * @Return           :
	 * @Description      : 요청한 소셜 타입 정보 반환
	 */
	OauthType getOauthType();

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            :
	 * @Return           : 소셜 로그인 팝업을 띄우기 위한 request url
	 * @Description      : 클라이언트의 요청을 받아 소셜 별 request url을 생성
	 */
	String createRequestUrl(String profile);

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            : OauthType
	 * @Return           : boolean
	 * @Description      : 소셜 별로 request url 정보를 가져올 수 있는 지 검증
	 */
	default boolean canRequest(OauthType oauthType) {
		return getOauthType().equals(oauthType);
	}
}
