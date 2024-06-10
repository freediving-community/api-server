package com.freediving.buddyservice.adapter.in.web.command;

import java.util.HashMap;
import java.util.Map;

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
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
@Tag(name = "Buddy Event Command", description = "버디 이벤트 Command 관련 API")
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
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = CreatedBuddyEventResponse.class))
			),
			@ApiResponse(responseCode = "3409", description = "이미 생성된 버디 모임과 시간 겹침.", ref = "#/components/responses/3409"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "인증 실패", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "403", description = "권한 없음 <br> - 정지된 사용자 <br> - 존재하지 않은 사용자", ref = "#/components/responses/403"),
			@ApiResponse(responseCode = "500", description = "서비스 에러", ref = "#/components/responses/500"),
		}
	)
	@PostMapping("")
	public ResponseEntity<ResponseJsonObject<CreatedBuddyEventResponse>> createBuddyEvent(
		@Valid @RequestBody CreateBuddyEventRequest request, HttpServletRequest httpServletRequest) {
		try {
			// 1. UserID 추출하기
			Object userIdObj = httpServletRequest.getAttribute("User-Id");
			if (userIdObj == null)
				throw new BuddyMeException(ServiceStatusCode.UNAUTHORIZED);

			Long userId = Long.parseLong(userIdObj.toString());

			if (userId == null || userId.equals(-1L))
				throw new BuddyMeException(ServiceStatusCode.UNAUTHORIZED);

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
					.genderType(request.getGenderType())
					.imageUrl(request.getImageUrl())
					.build());

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<CreatedBuddyEventResponse> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				createdBuddyEventResponse);

			return ResponseEntity.ok(response);
		} catch (BuddyMeException e) {
			throw e;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Operation(
		summary = "버디 이벤트 좋아요 설정/해지",
		description = "버디 이벤트 좋아요 설정/해지를 처리하고 버디 이벤트의 최종 좋아요 수치를 반환한다.  좋아요 설정이 되어있는 버디 이벤트에 좋아요를 하거나, 좋아요 해지 상태의 "
			+ "버디이벤트에 해지를 요청하면 좋아요 숫자가 -1이 리턴된다.(아무런 로직이 실행되지는 않는다.) 결과는 200 리턴이 된다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 이벤트 좋아요 설정/해지 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(example = "{ \"likeCount\" : 14 }"))

			),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "인증 실패", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "403", description = "권한 없음( 잘못된 사용자가 요청시 ) <br> - 정지된 사용자 <br> - 존재하지 않은 사용자", ref = "#/components/responses/403"),
			@ApiResponse(responseCode = "500", description = "서비스 에러", ref = "#/components/responses/500")
		}
	)
	@PostMapping("/like")
	public ResponseEntity<ResponseJsonObject> toggleBuddyEventLike(
		@Valid @RequestBody BuddyEventLikeToggleRequest request, HttpServletRequest httpServletRequest) {
		try {
			// 1. UserID 추출하기
			Object userIdObj = httpServletRequest.getAttribute("User-Id");
			if (userIdObj == null)
				throw new BuddyMeException(ServiceStatusCode.UNAUTHORIZED);

			Long userId = Long.parseLong(userIdObj.toString());

			if (userId == null || userId.equals(-1L))
				throw new BuddyMeException(ServiceStatusCode.UNAUTHORIZED);

			// 2. Use Case Command 전달.
			Integer count = buddyEventLikeActionUseCase.buddyEventLikeToggle(
				BuddyEventLikeToggleCommand.builder()
					.userId(userId)
					.eventId(request.getEventId())
					.likeStatus(request.isLikeStatus())
					.build());

			// 3. Command 요청 및 응답 리턴.
			Map<String, Integer> resMap = new HashMap<>();
			resMap.put("likeCount", count);
			ResponseJsonObject response = new ResponseJsonObject(ServiceStatusCode.OK, resMap);

			return ResponseEntity.ok(response);
		} catch (BuddyMeException e) {
			throw e;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}
	}
}
