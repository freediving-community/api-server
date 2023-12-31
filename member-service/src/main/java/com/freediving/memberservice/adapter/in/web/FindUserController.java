package com.freediving.memberservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.memberservice.application.port.in.FindUserCommand;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.domain.User;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class FindUserController {

	private final FindUserUseCase findUserUseCase;

	@GetMapping("/{userId}")
	ResponseEntity<User> findUserByUserId(@PathVariable Long userId) {
		FindUserCommand findUserCommand = FindUserCommand.builder()
			.userId(userId)
			.build();

		return ResponseEntity.ok(findUserUseCase.findUser(findUserCommand));
	}
}
