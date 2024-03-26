package com.freediving.memberservice.custom;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import com.freediving.common.domain.member.RoleLevel;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.OauthType;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
	long id() default 1L;

	String email() default "sasca37@naver.com";

	String profileImgUrl() default "";

	String nickname() default "sasca37";

	String content() default "안녕";

	OauthType oauthType() default OauthType.KAKAO;

	RoleLevel roleLevel() default RoleLevel.UNREGISTER;

	DiveType diveType() default DiveType.FREE_DIVE;

	int licenseLevel() default 0;

	String licenseImgUrl() default "";

	boolean licenseValidTF() default false;

	long confirmAdminId() default 1L;
}
