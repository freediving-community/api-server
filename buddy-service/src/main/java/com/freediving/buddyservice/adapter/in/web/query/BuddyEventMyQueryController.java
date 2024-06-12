package com.freediving.buddyservice.adapter.in.web.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingUseCase;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventlistingCardResponse;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
@Tag(name = "Buddy Event Query", description = "버디 이벤트 Query 관련 API")
public class BuddyEventMyQueryController {

	private final GetBuddyEventListingUseCase getBuddyEventListingUseCase;

	public static Long getUserId(HttpServletRequest httpServletRequest) {
		Object userIdObj = httpServletRequest.getAttribute("User-Id");
		Long userId = null;

		if (userIdObj != null) {
			try {
				userId = Long.parseLong(userIdObj.toString());
				if (userId.equals(-1L)) {
					userId = null;
				}
			} catch (NumberFormatException e) {
				// 예외 발생 시 userId를 null로 설정
				userId = null;
			}
		}

		return userId;
	}

	// API 리스팅 카드 조회 하기.
	@Operation(
		summary = "관심 버디 모임 조회 하기 ",
		description = "좋아요를 표시한 버디 모임을 리스팅 카드로 조회합니다."
			+ "조회되는 조건은 아래와 같습니다."
			+ "<br> - 좋아요 표시를 한 버디 모임"
			+ "<br> - 모임 시작 시간이 현재시간 이후 and 모집중 상태의 버디모임",

		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 모임 조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BuddyEventlistingCardResponse.class))
			),
			@ApiResponse(responseCode = "204", description = "조회 결과 없음", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "401", description = "JWT 토큰 없을 시", ref = "#/components/responses/401"),
			@ApiResponse(responseCode = "500", description = "서비스 에러", ref = "#/components/responses/500")
		}
	)
	@GetMapping("event/my/like")
	public ResponseEntity<ResponseJsonObject<QueryComponentListResponse>> getLikeBuddyEventListing(
		@RequestParam("pageNumber") @NotNull @Positive @Schema(example = "1") Integer pageNumber,
		@RequestParam("pageSize") @NotNull @Positive @Schema(example = "10") Integer pageSize,
		HttpServletRequest httpServletRequest) {
		try {
			// 1. UserID 추출하기
			Long userId = getUserId(httpServletRequest);

			if (userId == null)
				throw new BuddyMeException(ServiceStatusCode.UNAUTHORIZED);

			QueryComponentListResponse buddyEventListingResponse = getBuddyEventListingUseCase.getLikeBuddyEventListing(
				userId, pageNumber, pageSize
			);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<QueryComponentListResponse> response = new ResponseJsonObject<>(
				ServiceStatusCode.OK,
				buddyEventListingResponse);

			return ResponseEntity.ok(response);
		} catch (BuddyMeException e) {
			throw e;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}
	}

}
