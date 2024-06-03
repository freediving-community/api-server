package com.freediving.buddyservice.adapter.in.internallservice.query;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.internalservice.query.InternalUseCase;
import com.freediving.buddyservice.domain.query.UserConceptListResponse;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/internal")
@Tag(name = "Internal API", description = "Internal 서비스 호출 API")
public class InternalController {

	private final InternalUseCase internalUseCase;

	@Operation(
		summary = "사용자 선호 콘셉트 조회 ( getEventConcepts )",
		description = "사용자의 선호 콘셉트를 모두 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = UserConceptListResponse.class))
			),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/{userId}/concept")
	public ResponseEntity<ResponseJsonObject<UserConceptListResponse>> getEventConcepts(
		@Valid @NotNull @PathVariable(value = "userId") Long userId) {
		try {

			// 커멘트 생성 후 UseCase 전달
			UserConceptListResponse eventConcepts = internalUseCase.getEventConceptsForInternal(userId);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<UserConceptListResponse> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				eventConcepts);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}

	}

	@Operation(
		summary = "버디 모임 참여자 조회",
		description = "버디 모임 이벤트에 참여자(수락된 사용자)의 ID를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(example = "{ \"userIds\": [ 1, 2 ] }"))
			),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/{eventId}/participants")
	public ResponseEntity<ResponseJsonObject<Object>> getParticipantsOfEvent(
		@Valid @NotNull @PathVariable(value = "eventId") Long eventId) {
		try {

			List<Long> participants = internalUseCase.getParticipantsOfEvent(eventId);

			HashMap<String, List<Long>> map = new HashMap<>();

			map.put("userIds", participants);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<Object> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				map);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}

	}

}
