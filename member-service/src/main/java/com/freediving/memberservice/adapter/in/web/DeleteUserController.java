package com.freediving.memberservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.memberservice.application.port.in.DeleteUserUseCase;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/31
 * @Description    : 유저 정보 삭제
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/31        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
@Validated
@Tag(name = "User", description = "유저 관련 API")
public class DeleteUserController {

	private final DeleteUserUseCase deleteUserUseCase;

	@Operation(summary = "회원 탈퇴 API"
		, description = "JWT 정보를 기반으로 사용자 정보를 조회하여 해당 유저를 탈퇴한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@DeleteMapping("/users/me")
	public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user) {

		Long deleteUserCommand = user.userId();
		deleteUserUseCase.deleteUser(deleteUserCommand);

		return ResponseEntity.noContent().build();
	}
}
