package com.freediving.memberservice.fixture;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/28
 * @Description    : Spring Security Context 내 Authentication 객체를 반환하는 Fixture
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/28        sasca37       최초 생성
 */
public class SecurityContextFixture {
	public static User getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User)authentication.getPrincipal();
	}
}
