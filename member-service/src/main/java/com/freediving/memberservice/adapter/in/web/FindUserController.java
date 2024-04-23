package com.freediving.memberservice.adapter.in.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.dto.member.MemberFindUserResponse;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.FindNicknameResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.application.port.in.FindUserListQuery;
import com.freediving.memberservice.application.port.in.FindUserQuery;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.mapper.internal.FindUserMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Validated
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
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
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

	@Operation(summary = "사용자 정보 조회 API (Service 간 통신)"
		, description = "userId 정보를 기반으로 사용자 정보를 조회하여 반환한다. <br/>"
		+ "탈퇴 등으로 조회되지 않은 유저에 대해서도 기본 값을 생성하여 반환",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@GetMapping("/service/users")
	public ResponseEntity<ResponseJsonObject<List<MemberFindUserResponse>>> findUserListByUserIds(
		@RequestParam(value = "userIds") List<Long> userIdList,
		@RequestParam(value = "profileImg", required = false, defaultValue = "false") Boolean profileImgTF) {

		List<Long> uniqueUserIdList = userIdList.stream().distinct().toList();

		FindUserListQuery findUserListQuery = FindUserListQuery.builder()
			.userIds(uniqueUserIdList)
			.profileImgTF(profileImgTF)
			.build();
		// List<FindUserServiceResponse> findUserList = findUserUseCase.findUserListByQuery(findUserListQuery);
		List<MemberFindUserResponse> resp = findUserUseCase.findUserListByQuery(findUserListQuery).stream()
			.map(r -> FindUserMapper.INSTANCE.toCommonFindUserResponse(r)).collect(Collectors.toList());
		return ResponseEntity.ok(new ResponseJsonObject(ServiceStatusCode.OK, resp));
	}

	@Operation(summary = "사용자 닉네임 중복 조회 API"
		, description = "요청한 nickname의 중복여부를 확인하여 반환한다. <br/>"
		+ "닉네임은 한글, 영어, 숫자, 언더바(_)만 사용 가능하고 16자리까지 생성이 가능합니다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@GetMapping("/users/{nickname}")
	public ResponseEntity<ResponseJsonObject<FindNicknameResponse>> findNickname(
		@Pattern(regexp = "^[가-힣a-zA-Z0-9_]+$", message = "닉네임은 한글, 영어, 숫자, 밑줄(_)만 사용할 수 있습니다.")
		@Size(min = 1, max = 16, message = "닉네임은 1자 이상 16자 이하여야 합니다.")
		@PathVariable(name = "nickname") String nickname) {

		boolean isExistNickname = findUserUseCase.findNickname(nickname);

		FindNicknameResponse response = new FindNicknameResponse(!isExistNickname);

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, response));
	}
}
