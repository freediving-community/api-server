package com.freediving.buddyservice.adapter.in.web;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventUseCase;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 외부 web으로의 RestAPI 요청을 받아내는 Adapter 역할
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-27
 **/

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/event")
@Tag(name = "Buddy Event", description = "버디 이벤트 관련 API")
public class CreateBuddyEventController {

	private final CreateBuddyEventUseCase createBuddyEventUseCase;

	@Operation(
		summary = "버디 이벤트 생성하기",
		description = "새로운 버디 이벤트를 생성합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 이벤트 생성 성공",
				useReturnTypeSchema = true
			),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "403", ref = "#/components/responses/403"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@PostMapping("")
	public ResponseEntity<ResponseJsonObject<CreatedBuddyEvent>> createBuddyEvent(
		@Valid @RequestBody CreateBuddyEventRequest request) {
		try {
			// 1. JWT 유저 토큰에서 사용자 식별 ID 가져오기
			Random random = new Random();
			Long userId = random.nextLong();

			// 2. Use Case Command 전달.
			CreatedBuddyEvent createdBuddyEvent = createBuddyEventUseCase.createBuddyEvent(
				CreateBuddyEventCommand.builder()
					.userId(userId)
					.eventStartDate(request.getEventStartDate())
					.eventEndDate(request.getEventEndDate())
					.participantCount(request.getParticipantCount())
					.eventConcepts(request.getEventConcepts())
					.carShareYn(request.getCarShareYn())
					.comment(request.getComment())
					.build());

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<CreatedBuddyEvent> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				createdBuddyEvent);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}
	}
}
