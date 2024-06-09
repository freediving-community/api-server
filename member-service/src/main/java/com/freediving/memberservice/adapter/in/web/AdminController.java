package com.freediving.memberservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.FindUserLicenseResponse;
import com.freediving.memberservice.adapter.in.web.dto.UpdateUserLicenseStatusRequest;
import com.freediving.memberservice.application.port.in.AdminUseCase;
import com.freediving.memberservice.application.port.in.UpdateUserLicenseStatusCommand;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/04/20
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/04/20        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
@Tag(name = "Admin")
@Slf4j
@Validated
public class AdminController {

	private final AdminUseCase adminUseCase;

	@Operation(summary = "자격증 등록한 사용자 라이센스 정보 조회 API"
		, description = "가입 시 또는 회원정보 수정을 통해 자격증을 등록한 사용자 라이센스 정보를 조회한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@GetMapping("/users/licenses")
	// @PreAuthorize(value = "ADMIN")
	public ResponseEntity<ResponseJsonObject<FindUserLicenseResponse>> findUserLicenseSubmitList() {
		FindUserLicenseResponse resp = adminUseCase.findUserLicenseSubmitList();
		return ResponseEntity.ok(new ResponseJsonObject(ServiceStatusCode.OK, resp));
	}

	@Operation(summary = "라이센스 승인 / 거절 API"
		, description = "라이센스 요청한 정보를 조회하여 승인 / 거절한다. 단체명(orgName)은 해당 API에서 관리자가 입력하여 등록한다. </br>"
		+ "거절 시 거절에 대한 사유 입력은 필수 값으로 입력되고, 라이센스 승인 / 거절에 대한 결과는 알림이력에 저장된다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PatchMapping("/users/licenses")
	public ResponseEntity<ResponseJsonObject<?>> updateUserLicenseStatus(@Valid @RequestBody UpdateUserLicenseStatusRequest request,
		@AuthenticationPrincipal User user) {

		UpdateUserLicenseStatusCommand command = UpdateUserLicenseStatusCommand.builder()
			.adminUserId(user.userId())
			.licenseId(request.getLicenseId())
			.licenseLevel(request.getLicenseLevel())
			.orgName(request.getOrgName())
			.confirmTF(request.getConfirmTF())
			.rejectContent(request.getRejectContent())
			.build();

		adminUseCase.updateUserLicenseStatus(command);

		return ResponseEntity.ok(new ResponseJsonObject(ServiceStatusCode.OK, null));
	}


}
