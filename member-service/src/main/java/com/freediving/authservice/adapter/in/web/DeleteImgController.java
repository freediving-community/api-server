package com.freediving.authservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.adapter.in.web.dto.DeleteImgRequest;
import com.freediving.authservice.application.port.in.DeleteImgCommand;
import com.freediving.authservice.application.port.in.DeleteImgUseCase;
import com.freediving.common.config.annotation.WebAdapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/03
 * @Description    : 이미지 삭제 Controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class DeleteImgController {

	private final DeleteImgUseCase deleteImgUseCase;

	@Tag(name = "Internal", description = "내부 통신 API")
	@Operation(summary = "이미지 삭제 요청 내부 통신 API"
		, description = "이미지 단건, 다건 삭제 처리 (요청한 이미지가 존재하지 않는 경우에도 204 응답)",
		responses = {
			@ApiResponse(responseCode = "204", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "실패 - 권한 오류", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류", ref = "#/components/responses/500")
		})
	@DeleteMapping("/internal/imgs")
	public ResponseEntity<Void> deletePreSignedUrl(@RequestBody DeleteImgRequest request) {
		DeleteImgCommand command = DeleteImgCommand.builder()
			.imgUrlList(request.imgUrlList())
			.build();
		deleteImgUseCase.deleteImgs(command);
		return ResponseEntity.noContent().build();
	}
}
