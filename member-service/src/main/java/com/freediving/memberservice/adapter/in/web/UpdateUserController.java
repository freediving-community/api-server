package com.freediving.memberservice.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.adapter.in.web.dto.UpdateUserInfoRequest;
import com.freediving.memberservice.application.port.in.UpdateUserInfoCommand;
import com.freediving.memberservice.application.port.in.UpdateUserUseCase;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/26
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/26        sasca37       최초 생성
 */
@WebAdapter
@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "User", description = "유저 관련 API")
public class UpdateUserController {

	private final UpdateUserUseCase updateUserUseCase;

	@Operation(summary = "유저 정보 수정 API"
		, description = "다이브타입 [F (프리다이빙), S (스쿠버다이빙)], 라이센스, 다이빙 풀, 컨셉, 유저 정보 등의 정보를 수정한다. 모든 필드 값은 필수값이여야 합니다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PutMapping("/v1/users/info")
	public ResponseEntity<ResponseJsonObject<FindUserResponse>> updateUserInfo(@Valid @RequestBody UpdateUserInfoRequest request,
		@AuthenticationPrincipal User user) {
		UpdateUserInfoCommand command = UpdateUserInfoCommand.builder()
			.userId(user.userId())
			.diveType(request.getDiveType())
			.licenseLevel(request.getLicenseLevel())
			.licenseImgUrl(request.getLicenseImgUrl())
			.poolList(request.getPoolList())
			.conceptList(request.getConceptList())
			.profileImgUrl(request.getProfileImgUrl())
			.nickname(request.getNickname())
			.content(request.getContent())
			.build();
		FindUserResponse resp = updateUserUseCase.updateUserInfo(command);

		return ResponseEntity.ok(new ResponseJsonObject<>(ServiceStatusCode.OK, resp));
	}
}
