package com.freediving.memberservice.adapter.in.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserLicenceImgUrlRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserLicenceLevelRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserLicenceImgUrlCommand;
import com.freediving.memberservice.application.port.in.CreateUserLicenceLevelCommand;
import com.freediving.memberservice.application.port.in.CreateUserLicenceUseCase;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
@RequestMapping("/v1")
@Tag(name = "User")
public class CreateUserController {

	private final CreateUserUseCase createUserUseCase;
	private final CreateUserLicenceUseCase createUserLicenceUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/15
	 * @Param            : Oauth에서 생성된 UserRequest 정보
	 * @Return           : User 기본 정보를 담은 DTO 반환
	 * @Description      : Oauth 정보를 바탕으로 가입여부 확인 및 JWT 토큰 업데이트
	 */

	@PostMapping("/service/users/register")
	@Hidden
	public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
		CreateUserCommand command = CreateUserCommand.builder()
			.oauthType(request.getOauthType())
			.email(request.getEmail())
			.profileImgUrl(request.getProfileImgUrl())
			.build();
		User user = createUserUseCase.createOrGetUser(command);
		return CreateUserResponse.from(user);
	}

	@Operation(summary = "자격증 레벨 등록 API"
		, description = "자격증 레벨 정보를 request로 요청하여 자격증 레벨을 등록한다. <br/>"
		+ "자격증 레벨 : null (미입력), 0 : 자격증 없음, 1 : 1레벨, 2 : 2레벨, 3 : 3레벨, 4 : 4레벨, 5 : 강사",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류"),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류")
		})
	@PostMapping("/users/licence/level")
	public void createLicenceLevel(@Valid @RequestBody CreateUserLicenceLevelRequest request,
		@AuthenticationPrincipal User user) {

		Long userId = user.userId();
		CreateUserLicenceLevelCommand command = CreateUserLicenceLevelCommand.builder()
			.licenceLevel(request.getLicenceLevel())
			.build();

		createUserLicenceUseCase.createUserLicenceLevel(userId, command);
	}

	@Operation(summary = "자격증 프로필 이미지 URL 등록 API"
		, description = "유저가 업로드한 이미지 URL 정보를 받아 서버에 등록한다. <br/>"
		+ "자격증 레벨 API가 등록되어있지 않으면 (null) 400 오류가 발생한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류"),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류")
		})
	@PostMapping("/users/licence/img")
	public void createLicenceImgUrl(@Valid @RequestBody CreateUserLicenceImgUrlRequest request,
		@AuthenticationPrincipal User user) {

		Long userId = user.userId();
		CreateUserLicenceImgUrlCommand command = CreateUserLicenceImgUrlCommand.builder()
			.licenceImgUrl(request.getLicenceImgUrl())
			.build();

		createUserLicenceUseCase.createUserLicenceImgUrl(userId, command);
	}
}
