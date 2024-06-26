package com.freediving.memberservice.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserInfoRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserProfileRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommand;
import com.freediving.memberservice.application.port.in.CreateUserProfileCommand;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.application.port.out.service.buddy.BuddyUseCase;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : Oauth request 정보를 받아 User를 생성하는 Controller ( 외부 Request로 들어온 객체를 내부 Command 객체로 캡슐화)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@Tag(name = "User")
@Slf4j
public class CreateUserController {

	private final CreateUserUseCase createUserUseCase;
	private final BuddyUseCase buddyUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/15
	 * @Param            : Oauth에서 생성된 UserRequest 정보
	 * @Return           : User 기본 정보를 담은 DTO 반환
	 * @Description      : Oauth 정보를 바탕으로 가입여부 확인 및 JWT 토큰 업데이트
	 */

	@PostMapping("/v1/service/users/register")
	@Hidden
	public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
		CreateUserCommand command = CreateUserCommand.builder()
			.oauthType(request.getOauthType())
			.email(request.getEmail())
			.profileImgUrl(request.getProfileImgUrl())
			.providerId(request.getProviderId())
			.build();
		return createUserUseCase.createOrGetUser(command);
	}

	@Operation(summary = "유저 프로필 정보 등록 API"
		, description = "회원가입 시 유저 프로필 정보 등록한 적이 없는 경우 등록",
		responses = {
			@ApiResponse(responseCode = "201", description = "성공", ref = "#/components/responses/201"),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PostMapping("/v1/users/profile")
	public ResponseEntity<?> createUserProfile(@Valid @RequestBody CreateUserProfileRequest request,
		@AuthenticationPrincipal User user) {
		CreateUserProfileCommand command = CreateUserProfileCommand.builder()
			.userId(user.userId())
			.profileImgUrl(request.getProfileImgUrl())
			.nickname(request.getNickname())
			.content(request.getContent())
			.build();
		createUserUseCase.createUserProfile(command);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		ResponseJsonObject<?> response = new ResponseJsonObject<>(ServiceStatusCode.CREATED, location);
		return ResponseEntity.created(location).body(response);
	}

	@Operation(summary = "내 정보 등록 API"
		, description = "다이브타입 [F (프리다이빙), S (스쿠버다이빙)], 라이센스, 다이빙 풀, 컨셉, 유저 정보 등의 정보를 저장한다.",
		responses = {
			@ApiResponse(responseCode = "201", description = "성공", ref = "#/components/responses/201"),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PostMapping("/v1/users/info")
	public ResponseEntity<?> createUserInfo(@Valid @RequestBody CreateUserInfoRequest request,
		@AuthenticationPrincipal User user) {
		CreateUserInfoCommand command = CreateUserInfoCommand.builder()
			.userId(user.userId())
			.diveType(request.getDiveType())
			.licenseLevel(request.getLicenseLevel())
			.licenseImgUrl(request.getLicenseImgUrl())
			.poolList(request.getPoolList())
			.conceptList(request.getConceptList())
			.profileImgUrl(request.getProfileImgUrl())
			.nickname(request.getNickname())
			.content(request.getContent())
			.build();
		createUserUseCase.createUserInfo(command);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		ResponseJsonObject<?> response = new ResponseJsonObject<>(ServiceStatusCode.CREATED, location);
		return ResponseEntity.created(location).body(response);
	}

}
