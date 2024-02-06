package com.freediving.memberservice.adapter.in.web;

import static com.freediving.memberservice.fixture.SecurityContextFixture.*;
import static com.freediving.memberservice.fixture.UserFixture.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.custom.WithMockCustomUser;
import com.freediving.memberservice.domain.RoleLevel;
import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    : FindUserController 단위 테스트
 * 1. SecurityContext에 담겨있는 UserDetails 정보를 꺼내온다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */

@WebMvcTest(controllers = FindUserController.class)
class FindUserControllerUnitTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FindUserUseCase findUserUseCase;

	@Test
	@DisplayName("유저 조회 성공 테스트 - 일반 유저")
	@WithMockCustomUser(roleLevel = RoleLevel.UNREGISTER)
	void findCommonUserByIdSuccess() throws Exception {
		User user = getUserDetails();
		when(findUserUseCase.findUserDetailByQuery(any())).thenReturn(user);
		mockMvc.perform(get("/v1/users/me"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.email").value(VALID_EMAIL))
			.andExpect(jsonPath("$.data.oauthType").value(VALID_OAUTH_TYPE.name()))
			.andExpect(jsonPath("$.data.roleLevel").value(RoleLevel.UNREGISTER.getLevel()))
			.andDo(print());
	}

	@Test
	@DisplayName("유저 조회 성공 테스트 - 관리자")
	@WithMockCustomUser(roleLevel = RoleLevel.ADMIN)
	void findAdminUserByIdSuccess() throws Exception {
		User user = getUserDetails();
		when(findUserUseCase.findUserDetailByQuery(any())).thenReturn(user);
		mockMvc.perform(get("/v1/users/me"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.email").value(VALID_EMAIL))
			.andExpect(jsonPath("$.data.oauthType").value(VALID_OAUTH_TYPE.name()))
			.andExpect(jsonPath("$.data.roleLevel").value(RoleLevel.ADMIN.getLevel()))
			.andDo(print());
	}

	@Test
	@DisplayName("유저 조회 실패 테스트")
	@WithAnonymousUser
	void findCommonUserByIdFail() throws Exception {
		mockMvc.perform(get("/v1/users/me"))
			.andExpect(status().isUnauthorized())
			.andDo(print());
	}

}