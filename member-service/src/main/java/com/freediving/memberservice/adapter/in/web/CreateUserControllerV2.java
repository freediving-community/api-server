package com.freediving.memberservice.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserInfoRequestV2;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommandV2;
import com.freediving.memberservice.application.port.in.CreateUserUseCaseV2;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/20
 * @Description    : Oauth request 정보를 받아 User를 생성하는 Controller ( 외부 Request로 들어온 객체를 내부 Command 객체로 캡슐화)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/20        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@Tag(name = "User")
@Slf4j
public class CreateUserControllerV2 {

	private final CreateUserUseCaseV2 createUserUseCaseV2;

	@Operation(summary = "내 정보 등록 V2 API"
		, description = "다이브타입 [F (프리다이빙), S (스쿠버다이빙)], 라이센스, 다이빙 풀, 컨셉, 유저 정보 등의 정보를 저장한다. <br> <br>  "
		+ "나중에 하기 기능을 위해 null request 허용 (DiveType은 값 필수)",
		responses = {
			@ApiResponse(responseCode = "201", description = "성공", ref = "#/components/responses/201"),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PostMapping("/v2/users/info")
	public ResponseEntity<?> createUserInfoV2(@Valid @RequestBody CreateUserInfoRequestV2 request,
		@AuthenticationPrincipal User user) {

		final DiveType diveType = request.getDiveType();
		CreateUserInfoCommandV2 command = CreateUserInfoCommandV2.builder()
			.userId(user.userId())
			.diveType(diveType)
			.licenseLevel(request.getLicenseLevel())
			.licenseImgUrl(request.getLicenseImgUrl())
			.poolList(request.getPoolList())
			.conceptList(request.getConceptList())
			.build();

		createUserUseCaseV2.createUserInfoV2(command);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		ResponseJsonObject<?> response = new ResponseJsonObject<>(ServiceStatusCode.CREATED, location);
		return ResponseEntity.created(location).body(response);
	}

}
