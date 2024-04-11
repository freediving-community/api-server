package com.freediving.buddyservice.adapter.in.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.externalservice.query.GetBuddyEventConceptListUseCase;
import com.freediving.buddyservice.application.port.out.service.BuddyEventConceptListResponse;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/attribute")
@Tag(name = "Attribute", description = "버디 이벤트 속성 API")
public class BuddyEventAttributeController {

	private final GetBuddyEventConceptListUseCase getBuddyEventConceptListUseCase;

	@Operation(
		summary = "이벤트 콘셉트 조회",
		description = "이벤트에 사용되는 콘셉트를 모두 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				useReturnTypeSchema = true
			),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/concept")
	public ResponseEntity<ResponseJsonObject<BuddyEventConceptListResponse>> getEventConcepts() {

		try {

			// 커멘트 생성 후 UseCase 전달
			BuddyEventConceptListResponse eventConcepts = getBuddyEventConceptListUseCase.getEventConcepts();

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<BuddyEventConceptListResponse> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				eventConcepts);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}

	}

}
