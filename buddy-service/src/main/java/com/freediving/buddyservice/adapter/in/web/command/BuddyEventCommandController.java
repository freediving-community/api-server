package com.freediving.buddyservice.adapter.in.web.command;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.adapter.in.web.command.dto.BuddyEventLikeToggleRequest;
import com.freediving.buddyservice.adapter.in.web.command.dto.CreateBuddyEventRequest;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleCommand;
import com.freediving.buddyservice.application.port.in.web.command.like.BuddyEventLikeToggleUseCase;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
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
public class BuddyEventCommandController {

	private final CreateBuddyEventUseCase createBuddyEventUseCase;
	private final BuddyEventLikeToggleUseCase buddyEventLikeActionUseCase;

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
	public ResponseEntity<ResponseJsonObject<CreatedBuddyEventResponse>> createBuddyEvent(
		@Valid @RequestBody CreateBuddyEventRequest request) {
		try {
			// 1. JWT 유저 토큰에서 사용자 식별 ID 가져오기
			Random random = new Random();
			Long userId = random.nextLong();

			// 2. Use Case Command 전달.
			CreatedBuddyEventResponse createdBuddyEventResponse = createBuddyEventUseCase.createBuddyEvent(
				CreateBuddyEventCommand.builder()
					.userId(userId)
					.eventStartDate(request.getEventStartDate())
					.eventEndDate(request.getEventEndDate())
					.participantCount(request.getParticipantCount())
					.buddyEventConcepts(request.getBuddyEventConcepts())
					.carShareYn(request.getCarShareYn())
					.kakaoRoomCode(request.getKakaoRoomCode())
					.comment(request.getComment())
					.freedivingLevel(request.getFreedivingLevel())
					.divingPools(request.getDivingPools())
					.build());

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<CreatedBuddyEventResponse> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				createdBuddyEventResponse);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}
	}

	@Operation(
		summary = "버디 이벤트 좋아요 설정/해지",
		description = "버디 이벤트 좋아요 설정/해지를 한다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 이벤트 좋아요 설정/해지 성공",
				useReturnTypeSchema = true
			),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "403", ref = "#/components/responses/403"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@PostMapping("/like")
	public ResponseEntity<ResponseJsonObject> toggleBuddyEventLike(
		@Valid @RequestBody BuddyEventLikeToggleRequest request) {
		try {
			// 1. JWT 유저 토큰 사용자 식별 ID 가져오기
			Random random = new Random();
			Long userId = random.nextLong();

			// 2. Use Case Command 전달.
			buddyEventLikeActionUseCase.toggleBuddyEventLike(
				BuddyEventLikeToggleCommand.builder()
					.userId(userId)
					.eventId(request.getEventId())
					.likeStatus(request.isLikeStatus())
					.build());

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject response = new ResponseJsonObject(ServiceStatusCode.OK, null);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}
	}
}
