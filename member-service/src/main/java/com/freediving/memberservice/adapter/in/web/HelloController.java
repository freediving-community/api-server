package com.freediving.memberservice.adapter.in.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.util.JwtTokenUtils;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserUseCase;
import com.freediving.memberservice.domain.OauthType;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Ping", description = "MemberService Health Check")
@RequiredArgsConstructor
@RequestMapping("/sample")
public class HelloController {

	private final CreateUserUseCase createUserUseCase;
	@Value("${jwt.key}")
	private String key;

	@GetMapping("/")
	public String hello() {
		return "Hello";
	}

	@PostMapping("/token")
	@Hidden
	public String generateSampleUser() {
		CreateUserCommand command = CreateUserCommand.builder()
			.oauthType(OauthType.KAKAO)
			.email("sasca37@naver.com")
			.profileImgUrl("https://img.com")
			.build();
		CreateUserResponse response = createUserUseCase.createOrGetUser(command);
		return JwtTokenUtils.generateRefreshToken(String.valueOf(response.getUserId()),
			String.valueOf(response.getOauthType()), key);
	}
}
