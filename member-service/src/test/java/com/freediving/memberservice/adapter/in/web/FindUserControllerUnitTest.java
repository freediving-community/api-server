package com.freediving.memberservice.adapter.in.web;

import static com.freediving.memberservice.fixture.SecurityContextFixture.*;
import static com.freediving.memberservice.fixture.UserFixture.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.custom.WithMockCustomUser;
import com.freediving.memberservice.domain.RoleLevel;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.fixture.FindUserServiceResponseFixture;

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
	@DisplayName("유저 조회 실패 테스트 - 인증되지 않은 사용자의 경우 401 에러 반환")
	@WithAnonymousUser
	void findCommonUserByIdFail() throws Exception {
		mockMvc.perform(get("/v1/users/me"))
			.andExpect(status().isUnauthorized())
			.andDo(print());
	}

	@Test
	@DisplayName("유저 조회 인터페이스 성공 테스트 - id 값이 순서대로 요청 온 경우 요청 온 순서대로 반환")
	@WithMockCustomUser(roleLevel = RoleLevel.UNREGISTER)
	void findUserServiceByIdAndParamAscSuccess() throws Exception {
		List<String> userIdList = Arrays.asList("1", "2", "3");

		String param = createUserIdParam(userIdList);
		List<FindUserServiceResponse> findUserList = createFindUserList(userIdList);

		when(findUserUseCase.findUserListByQuery(any())).thenReturn(findUserList);
		mockMvc.perform(get("/v1/service/users").param("userIds", param))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].userId").value(1))
			.andExpect(jsonPath("$.data[1].userId").value(2))
			.andExpect(jsonPath("$.data[2].userId").value(3))
			.andDo(print());
	}

	@Test
	@DisplayName("유저 조회 인터페이스 실패 테스트 - id 값에 잘못된 값이 들어온 경우 400 에러 반환")
	@WithMockCustomUser(roleLevel = RoleLevel.UNREGISTER)
	void findUserServiceByIdFail() throws Exception {
		List<String> userIdList = Arrays.asList("1", "2", "3", "A");

		String param = createUserIdParam(userIdList);

		mockMvc.perform(get("/v1/service/users").param("userIds", param))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@Test
	@DisplayName("유저 조회 인터페이스 실패 테스트 - 파라미터가 없는 경우 400 에러 반환")
	@WithMockCustomUser(roleLevel = RoleLevel.UNREGISTER)
	void findUserServiceByNoParamFail() throws Exception {
		mockMvc.perform(get("/v1/service/users"))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}

	@Test
	@DisplayName("유저 조회 인터페이스 성공 테스트 - id 값이 순서대로 들어오지 않은 경우 요청 온 순서대로 반환")
	@WithMockCustomUser(roleLevel = RoleLevel.UNREGISTER)
	void findUserServiceByIdAndParamRandomSuccess() throws Exception {
		List<String> userIdList = Arrays.asList("2", "3", "1");

		String param = createUserIdParam(userIdList);
		List<FindUserServiceResponse> findUserList = createFindUserList(userIdList);

		when(findUserUseCase.findUserListByQuery(any())).thenReturn(findUserList);
		mockMvc.perform(get("/v1/service/users").param("userIds", param))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].userId").value(2))
			.andExpect(jsonPath("$.data[1].userId").value(3))
			.andExpect(jsonPath("$.data[2].userId").value(1))
			.andDo(print());
	}

	@Test
	@DisplayName("유저 조회 인터페이스 성공 테스트 - id 값이 중복되어 들어온 경우 필터링 반환")
	@WithMockCustomUser(roleLevel = RoleLevel.UNREGISTER)
	void findUserServiceByIdAndParamDupSuccess3() throws Exception {
		List<String> userIdList = Arrays.asList("2", "3", "1", "4", "5", "6", "2", "2", "1");
		List<String> uniqueUserIdList = userIdList.stream().distinct().toList();

		String param = createUserIdParam(uniqueUserIdList);
		List<FindUserServiceResponse> findUserList = createFindUserList(uniqueUserIdList);

		when(findUserUseCase.findUserListByQuery(any())).thenReturn(findUserList);
		mockMvc.perform(get("/v1/service/users").param("userIds", param))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].userId").value(2))
			.andExpect(jsonPath("$.data[1].userId").value(3))
			.andExpect(jsonPath("$.data[2].userId").value(1))
			.andExpect(jsonPath("$.data.*", hasSize(6)))
			.andDo(print());
	}

	private String createUserIdParam(List<String> userIdList) {
		return userIdList.stream()
			.map(String::valueOf)
			.collect(Collectors.joining(","));
	}

	private List<FindUserServiceResponse> createFindUserList(List<String> userIdList) {
		return userIdList.stream()
			.map(id -> FindUserServiceResponseFixture.createFindUserServiceResponseByUserId(Long.valueOf(id)))
			.collect(Collectors.toList());
	}

}