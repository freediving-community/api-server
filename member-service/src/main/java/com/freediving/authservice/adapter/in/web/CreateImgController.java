package com.freediving.authservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.adapter.in.web.dto.CreateImgRequest;
import com.freediving.authservice.adapter.in.web.dto.CreateImgResponse;
import com.freediving.authservice.application.port.in.CreateImgCommand;
import com.freediving.authservice.application.port.in.CreateImgUseCase;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : 이미지 Presigned URL 정보를 생성하는 컨트롤러
 * 					( AWS S3를 사용하지만, 추후 CloudFlare 전환 가능성 존재)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/img")
@Slf4j
@Tag(name = "Image", description = "이미지 Presigned URL 정보를 생성")
public class CreateImgController {

	private final CreateImgUseCase imgUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/21
	 * @Param            : PreSigned URL 생성을 위한 Request DTO
	 * @Return           : PreSigned URL
	 * @Description      : 이미지 정보를 전달받아 PreSigned URL 정보 반환
	 */

	@Operation(summary = "이미지 PreSigned URL 생성 API"
		, description = "이미지 업로드 요청할 PreSigned URL 정보를 반환한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@PostMapping
	public ResponseEntity<ResponseJsonObject<CreateImgResponse>> createPreSignedUrl(
		@Valid @RequestBody CreateImgRequest createImgRequest, @AuthenticationPrincipal User user) {
		CreateImgCommand createImgCommand = CreateImgCommand.builder()
			.userId(user.userId())
			.directory(createImgRequest.directory())
			.ext(createImgRequest.ext())
			.build();
		CreateImgResponse response = imgUseCase.createPreSignedUrl(createImgCommand);
		ResponseJsonObject responseJsonObject = new ResponseJsonObject(ServiceStatusCode.OK, response);
		return ResponseEntity.ok(responseJsonObject);
	}
}
