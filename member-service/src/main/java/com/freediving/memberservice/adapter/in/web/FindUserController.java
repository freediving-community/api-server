package com.freediving.memberservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.memberservice.application.port.in.FindUserQuery;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.domain.User;

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

	@GetMapping("/users/me")
	ResponseEntity<User> findUserByUserId(@AuthenticationPrincipal User user) {
		FindUserQuery findUserQuery = FindUserQuery.builder().userId(user.userId()).build();
		return ResponseEntity.ok(findUserUseCase.findUserById(findUserQuery));
	}
}
