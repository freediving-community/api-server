package com.freediving.memberservice.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.freediving.memberservice.adapter.in.web.CreateUserController;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserRequest;
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

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!StringUtils.equals(profile, "local")) {
			return;
		}
		createUserController.createUser(CreateUserFixture.createTestUser(OauthType.KAKAO, "test1@naver.com",
			"https://d1pjflw6c3jt4r.cloudfront.net", "1234567a"));
		createUserController.createUser(CreateUserFixture.createTestUser(OauthType.KAKAO, "test2@naver.com",
			"https://d1pjflw6c3jt4r.cloudfront.net", "1234568b"));
		createUserController.createUser(CreateUserFixture.createTestUser(OauthType.NAVER, "test3@naver.com",
			"https://d1pjflw6c3jt4r.cloudfront.net", "1234569c"));
		createUserController.createUser(CreateUserFixture.createTestUser(OauthType.NAVER, "test4@naver.com",
			"https://d1pjflw6c3jt4r.cloudfront.net", "1234570d"));
		createUserController.createUser(CreateUserFixture.createTestUser(OauthType.GOOGLE, "test5@naver.com",
			"https://d1pjflw6c3jt4r.cloudfront.net", "1234571e"));
		createUserController.createUser(CreateUserFixture.createTestUser(OauthType.GOOGLE, "test6@naver.com",
			"https://d1pjflw6c3jt4r.cloudfront.net", "1234572f"));

	}

	class CreateUserFixture {
		public static CreateUserRequest createTestUser(OauthType oauthType, String email, String profileImgUrl,
			String providerId) {
			return new CreateUserRequest(oauthType, email, profileImgUrl, providerId);
		}
	}
}
