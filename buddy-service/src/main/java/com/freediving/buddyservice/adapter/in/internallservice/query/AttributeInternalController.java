package com.freediving.buddyservice.adapter.in.internallservice.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.externalservice.query.GetBuddyEventConceptListUseCase;
import com.freediving.buddyservice.domain.query.BuddyEventConceptListResponse;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/internal")
@Tag(name = "Attribute Internal", description = "Internal 서비스 호출 API")
public class AttributeInternalController {

	private final GetBuddyEventConceptListUseCase getBuddyEventConceptListUseCase;

	@Operation(
		summary = "이벤트 콘셉트 조회 ( getEventConcepts )",
		description = "이벤트에 사용되는 콘셉트를 모두 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BuddyEventConceptListResponse.class))
			),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/concept")
	public ResponseEntity<ResponseJsonObject<BuddyEventConceptListResponse>> getEventConcepts() {

		try {

			// 커멘트 생성 후 UseCase 전달
			BuddyEventConceptListResponse eventConcepts = getBuddyEventConceptListUseCase.getEventConceptsForInternal();

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<BuddyEventConceptListResponse> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				eventConcepts);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}

	}

}