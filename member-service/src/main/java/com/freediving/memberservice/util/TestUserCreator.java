package com.freediving.memberservice.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.freediving.authservice.application.port.out.CreateTokenPort;
import com.freediving.memberservice.adapter.in.web.CreateUserController;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.domain.OauthType;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/15
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/15        sasca37       최초 생성
 */

@Component
public class TestUserCreator implements InitializingBean {

	@Value("${spring.profiles.active}")
	private String profile;

	@Autowired
	private CreateUserController createUserController;

	@Autowired
	private CreateTokenPort createTokenPort;

	private static final String DEFAULT_IMG_URL = "https://d1pjflw6c3jt4r.cloudfront.net";

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!StringUtils.equalsAny(profile, "local", "dev")) {
			return;
		}
		CreateUserResponse test1 = createUser(OauthType.KAKAO, "test1@naver.com", DEFAULT_IMG_URL, "123a4567");
		createToken(test1);

		CreateUserResponse test2 = createUser(OauthType.KAKAO, "test2@naver.com", DEFAULT_IMG_URL, "123b4567");
		createToken(test2);

		CreateUserResponse test3 = createUser(OauthType.KAKAO, "test3@naver.com", DEFAULT_IMG_URL, "123c4567");
		createToken(test3);

		CreateUserResponse test4 = createUser(OauthType.KAKAO, "test4@naver.com", DEFAULT_IMG_URL, "123d4567");
		createToken(test4);

		CreateUserResponse test5 = createUser(OauthType.NAVER, "test5@naver.com", DEFAULT_IMG_URL, "123e4567");
		createToken(test5);

		CreateUserResponse test6 = createUser(OauthType.NAVER, "test6@naver.com", DEFAULT_IMG_URL, "123f4567");
		createToken(test6);

		CreateUserResponse test7 = createUser(OauthType.NAVER, "test7@naver.com", DEFAULT_IMG_URL, "123g4567");
		createToken(test7);

		CreateUserResponse test8 = createUser(OauthType.NAVER, "test8@naver.com", DEFAULT_IMG_URL, "123h4567");
		createToken(test8);

		CreateUserResponse test9 = createUser(OauthType.GOOGLE, "test9@naver.com", DEFAULT_IMG_URL, "123i4567");
		createToken(test9);

		CreateUserResponse test10 = createUser(OauthType.GOOGLE, "test10@naver.com", DEFAULT_IMG_URL, "123j4567");
		createToken(test10);

	}

	private CreateUserResponse createUser(OauthType oauthType, String email, String imgUrl, String providerId) {
		return createUserController.createUser(CreateUserFixture.createTestUser(oauthType, email, imgUrl, providerId));
	}

	public void createToken(CreateUserResponse resp) {
		createTokenPort.createTokens(String.valueOf(resp.getUserId()), resp.getOauthType());
	}

	class CreateUserFixture {
		public static CreateUserRequest createTestUser(OauthType oauthType, String email, String profileImgUrl,
			String providerId) {
			return new CreateUserRequest(oauthType, email, profileImgUrl, providerId);
		}
	}
}
