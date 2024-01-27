package com.freediving.memberservice.fixture;

import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.RoleLevel;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */
public class UserFixture {

	public static final String VALID_EMAIL = "sasca37@naver.com";
	public static final String INVALID_EMAIL = "sasca37/naver.com";
	public static final OauthType VALID_OAUTH_TYPE = OauthType.KAKAO;
	public static final OauthType INVALID_OAUTH_TYPE = OauthType.from("KEKAO");

	public static final String OPTIONAL_PROFILE_IMG_URL = null;

	public static final RoleLevel DEFAULT_ROLE_LEVEL = RoleLevel.UNREGISTER;

}
