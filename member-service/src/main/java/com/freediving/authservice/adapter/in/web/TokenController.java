package com.freediving.authservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.adapter.in.web.dto.CreateImgResponse;
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
@RequestMapping("/v1")
@Slf4j
@Tag(name = "Token", description = "JWT 토큰 관리 API")
public class TokenController {

	private final TokenUseCase tokenUseCase;

	@Operation(summary = "JWT 액세스 토큰 업데이트 API"
		, description = "JWT 액세스 토큰을 재생성하여 반환한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PatchMapping("/tokens")
	public ResponseEntity<ResponseJsonObject<CreateImgResponse>> updateTokens(@AuthenticationPrincipal User user) {
		String accessToken = tokenUseCase.updateTokens(user.userId(), user.oauthType().name());
		UpdateTokenResponse response = new UpdateTokenResponse(accessToken);
		ResponseJsonObject responseJsonObject = new ResponseJsonObject(ServiceStatusCode.OK, response);
		return ResponseEntity.ok(responseJsonObject);
	}
}