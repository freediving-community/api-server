package com.freediving.memberservice.fixture;

import com.freediving.common.domain.member.RoleLevel;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicense;

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

	public static final Long DEFAULT_ID = 1L;
	public static final String VALID_EMAIL = "sasca37@naver.com";
	public static final String INVALID_EMAIL = "sasca37/naver.com";
	public static final OauthType VALID_OAUTH_TYPE = OauthType.KAKAO;
	public static final String PROVIDER_ID = "1";
	public static final OauthType INVALID_OAUTH_TYPE = OauthType.from("KEKAO");

	public static final String OPTIONAL_PROFILE_IMG_URL = null;

	public static final RoleLevel DEFAULT_ROLE_LEVEL = RoleLevel.UNREGISTER;

	public static final UserLicense DEFAULT_USER_LICENSE = null;

	public static User createUserFromCommand(CreateUserCommand command) {
		return new User(DEFAULT_ID, command.getEmail(), command.getProfileImgUrl(), null, null,
			command.getOauthType(), null);
	}
}
