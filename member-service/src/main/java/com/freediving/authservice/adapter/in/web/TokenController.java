package com.freediving.authservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.adapter.in.web.dto.UpdateTokenResponse;
import com.freediving.authservice.application.port.in.TokenUseCase;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/04/20
 * @Description    : JWT 토큰을 관리하는 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/04/20        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

	private final TokenUseCase tokenUseCase;

	@Tag(name = "Token", description = "JWT 토큰 관리 API")
	@Operation(summary = "JWT 액세스 토큰 업데이트 API"
		, description = "리프레시 토큰 정보가 사용 가능한 경우 JWT 액세스 토큰을 재생성하여 반환한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PatchMapping("/v1/tokens")
	public ResponseEntity<ResponseJsonObject<UpdateTokenResponse>> updateTokens(@AuthenticationPrincipal User user) {
		String accessToken = tokenUseCase.updateTokens(user.userId(), user.oauthType().name());
		UpdateTokenResponse response = new UpdateTokenResponse(accessToken);
		ResponseJsonObject responseJsonObject = new ResponseJsonObject(ServiceStatusCode.OK, response);
		return ResponseEntity.ok(responseJsonObject);
	}

	@GetMapping("/sample/token/{userId}")
	@Tag(name = "Ping")
	@Operation(summary = "사용자가 존재하는 경우 액세스토큰을 생성하여 반환"
		, description = "개발서버의 경우 샘플 사용자 존재")
	public String getRefreshToken(@PathVariable("userId") Long userId) {
		return tokenUseCase.findRefreshTokenByUserId(userId);
	}
}
