package com.freediving.memberservice.adapter.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;
import com.freediving.memberservice.application.port.in.FindUserListQuery;
import com.freediving.memberservice.application.port.in.FindUserQuery;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 클라이언트 또는 다른 서비스에서 필요한 유저 정보를 반환
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
@Tag(name = "User", description = "유저 관련 API")
public class FindUserController {

	private final FindUserUseCase findUserUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/02/06
	 * @Param            : Security Authentication
	 * @Return           : User 조회 응답 DTO
	 * @Description      : 사용자 정보에 조회에 대한 응답 (자격증, 소셜 정보 등)
	 */

	@Operation(summary = "사용자 정보 조회 API"
		, description = "JWT 정보를 기반으로 사용자 정보를 조회하여 반환한다. <br/>"
		+ "응답 정보에는 사용자 ID, 이메일, 프로필 이미지, 닉네임, 소셜 정보, 라이센스 정보 등이 들어있다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류"),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류")
		})
	@GetMapping("/users/me")
	public ResponseEntity<ResponseJsonObject<FindUserResponse>> findUserByUserId(@AuthenticationPrincipal User user) {
		FindUserQuery findUserQuery = FindUserQuery.builder().userId(user.userId()).build();
		User findUser = findUserUseCase.findUserDetailByQuery(findUserQuery);
		FindUserResponse findUserResponse = FindUserResponse.from(findUser);

		return ResponseEntity.ok(new ResponseJsonObject(ServiceStatusCode.OK, findUserResponse));
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/02/27
	 * @Param            : userId 리스트 정보, profileImg 사용 여부
	 * @Return           : 중복 제거 및 요청 순서에 맞는 유저 정보
	 * @Description      : 탈퇴 등으로 조회되지 않은 유저에 대해서도 기본 값을 생성하여 반환
	 */
	@GetMapping("/service/users")
	@Hidden
	public ResponseEntity<ResponseJsonObject<List<FindUserServiceResponse>>> findUserListByUserIds(
		@RequestParam(value = "userIds") List<Long> userIdList,
		@RequestParam(value = "profileImg", required = false, defaultValue = "false") Boolean profileImgTF) {

		List<Long> uniqueUserIdList = userIdList.stream().distinct().toList();

		FindUserListQuery findUserListQuery = FindUserListQuery.builder()
			.userIds(uniqueUserIdList)
			.profileImgTF(profileImgTF)
			.build();

		List<FindUserServiceResponse> findUserList = findUserUseCase.findUserListByQuery(findUserListQuery);

		return ResponseEntity.ok(new ResponseJsonObject(ServiceStatusCode.OK, findUserList));
	}
}
