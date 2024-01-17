package com.freediving.memberservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.memberservice.application.port.in.FindUserQuery;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.domain.User;

import lombok.RequiredArgsConstructor;

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
@RequestMapping("/v1/users")
public class FindUserController {

	private final FindUserUseCase findUserUseCase;

	@GetMapping("/{userId}")
	ResponseEntity<User> findUserByUserId(@PathVariable Long userId) {
		FindUserQuery findUserQuery = FindUserQuery.builder().userId(userId).build();
		return ResponseEntity.ok(findUserUseCase.findUser(findUserQuery));
	}
}
