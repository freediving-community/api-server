package com.freediving.memberservice.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserRequest;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Hidden;
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
@Hidden
public class CreateUserController {

	private final CreateUserUseCase createUserUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/15
	 * @Param            : Oauth에서 생성된 UserRequest 정보
	 * @Return           : User 기본 정보 및 JWT 토큰 정보를 담은 DTO 반환
	 * @Description      : Oauth 정보를 바탕으로 가입여부 확인 및 JWT 토큰 업데이트
	 */

	@PostMapping("/service/users/register")
	public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
		CreateUserCommand command = CreateUserCommand.builder()
			.oauthType(request.getOauthType())
			.email(request.getEmail())
			.profileImgUrl(request.getProfileImgUrl())
			.refreshToken(request.getRefreshToken())
			.build();
		User user = createUserUseCase.createOrUpdateUser(command);
		return CreateUserResponse.from(user);
	}

}
