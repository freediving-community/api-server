package com.freediving.memberservice.custom;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.RoleLevel;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default 1L;

    String email() default "sasca37@naver.com";

    String profileImgUrl() default "";

    RoleLevel roleLevel() default RoleLevel.UNREGISTER;

    OauthType oauthType() default OauthType.KAKAO;

}
