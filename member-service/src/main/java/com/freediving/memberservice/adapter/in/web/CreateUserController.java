package com.freediving.memberservice.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class CreateUserController {

	private final CreateUserUseCase userUseCase;

	@PostMapping("/register")
	public void createUser(@RequestBody CreateUserRequest request) {
		CreateUserCommand command = CreateUserCommand.builder()
			.oauthType(request.getOauthType())
			.email(request.getEmail())
			.profileImgUrl(request.getProfileImgUrl())
			.build();
		userUseCase.createUser(command);
	}

}
