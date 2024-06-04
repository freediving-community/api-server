package com.freediving.buddyservice.adapter.in.web.query;

import java.time.LocalDateTime;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.adapter.in.web.query.dto.GetBuddyEventListingRequest;
import com.freediving.buddyservice.application.port.in.web.query.BuddyEventDetailCommand;
import com.freediving.buddyservice.application.port.in.web.query.BuddyEventDetailUseCase;
import com.freediving.buddyservice.application.port.in.web.query.home.GetBuddyEventCarouselUseCase;
import com.freediving.buddyservice.application.port.in.web.query.home.GetHomeActiveBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.query.home.GetHomePreferencePoolBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.query.home.GetHomeWeeklyBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingUseCase;
import com.freediving.buddyservice.domain.query.QueryBuddyEventDetailResponse;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.QueryPreferencePoolCarouselResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventCarouselCardResponse;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "Buddy Event Query", description = "버디 이벤트 Query 관련 API")
public class BuddyEventQueryController {

	private final GetBuddyEventListingUseCase getBuddyEventListingUseCase;
	private final GetBuddyEventCarouselUseCase getBuddyEventCarouselUseCase;
	private final BuddyEventDetailUseCase buddyEventDetailUseCase;

	/* API  캐로셀 카드 조회 하기.
	 *   - 메인 홈 N명의 다이버가 버디를 찾고 있어요.
	 *     - carousel/
	 *   - 메인 홈 이번주에 프리다이빙 어때요?
	 *   - 메인 홈 [DivingPool Name]에 같이 갈래요?
	 *   -
	 */

	// API 캐로셀 심플 카드 조회 하기.

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

	@Operation(
		summary = "버디 이벤트 상세정보 조회하기. ",
		description = "버디 매칭 상세정보를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 매칭 조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = QueryBuddyEventDetailResponse.class))
			),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("event/{eventId}")
	public ResponseEntity<ResponseJsonObject<QueryBuddyEventDetailResponse>> getBuddyEventDatail(
		@Valid @NotNull @PathVariable(name = "eventId") Long eventId, HttpServletRequest httpServletRequest) {
		try {
			// 1. UserID 추출하기
			Long userId = getUserId(httpServletRequest);

			QueryBuddyEventDetailResponse buddyEventDetail = buddyEventDetailUseCase.getBuddyEventDetail(
				BuddyEventDetailCommand.builder().eventId(eventId).userId(userId).build()
			);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<QueryBuddyEventDetailResponse> response = new ResponseJsonObject<>(
				ServiceStatusCode.OK,
				buddyEventDetail);

			return ResponseEntity.ok(response);
		} catch (BuddyMeException e) {
			throw e;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}
	}

	// API 리스팅 카드 조회 하기.
	@Operation(
		summary = "버디 이벤트 버디 매칭 조회 하기 ",
		description = "버디 매칭을 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 매칭 조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BuddyEventlistingCardResponse.class))
			),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("event/listing")
	public ResponseEntity<ResponseJsonObject<QueryComponentListResponse>> getBuddyEventListing(
		@Valid @ParameterObject
		@ModelAttribute GetBuddyEventListingRequest request, HttpServletRequest httpServletRequest) {
		try {
			// 1. UserID 추출하기
			Long userId = getUserId(httpServletRequest);

			QueryComponentListResponse buddyEventListingResponse = getBuddyEventListingUseCase.getBuddyEventListing(
				userId,
				GetBuddyEventListingCommand.builder()
					.eventStartDate(request.getEventStartDate())
					.eventEndDate(request.getEventEndDate())
					.buddyEventConcepts(request.getBuddyEventConcepts())
					.carShareYn(request.getCarShareYn())
					.freedivingLevel(request.getFreedivingLevel())
					.divingPools(request.getDivingPools())
					.sortType(request.getSortType())
					.genderType(request.getGenderType())
					.pageNumber(request.getPageNumber())
					.pageSize(request.getPageSize())
					.build()
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

	@Operation(
		summary = "메인 홈 \"이번주 프리다이빙 어때요\" 조회 ",
		description = "메인 홈에서 \"이번주 프리다이빙 어때요\" 영역의 이번주 버디 모임을 조회한다. 조회 파라미터중 시작 시간은 1페이지 조회 시작 시간을 다음 페이지 부터 동일 한 시간으로 넘겨주셔야 페이징 흐름에서 일관성있게 응답이 됩니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BuddyEventCarouselCardResponse.class))),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/home/weekly")
	public ResponseEntity<ResponseJsonObject<QueryComponentListResponse>> getHomeWeekly(
		HttpServletRequest httpServletRequest,
		@RequestParam(value = "eventStartDate") @NotNull @Schema(example = "2024-06-01T00:00:00") LocalDateTime eventStartDate,
		@RequestParam("pageNumber") @NotNull @Positive @Schema(example = "1") Integer pageNumber,
		@RequestParam("pageSize") @NotNull @Positive @Schema(example = "10") Integer pageSize) {
		try {
			// 1. UserID 추출하기
			Long userId = getUserId(httpServletRequest);

			QueryComponentListResponse homeWeekly = getBuddyEventCarouselUseCase.getHomeWeekly(userId,
				GetHomeWeeklyBuddyEventCommand.builder()
					.eventStartDate(eventStartDate)
					.pageNumber(pageNumber)
					.pageSize(pageSize)
					.build()
			);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<QueryComponentListResponse> response = new ResponseJsonObject<>(
				ServiceStatusCode.OK, homeWeekly);

			return ResponseEntity.ok(response);

		} catch (BuddyMeException be) {
			throw be;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}

	}

	@Operation(
		summary = "메인 홈 사용자 선호 풀장 버디 모임 조회 ",
		description = "메인 홈 사용자 선호 풀장 버디 모임 조회. 조회 파라미터중 시작 시간은 1페이지 조회 시작 시간을 다음 페이지 부터 동일 한 시간으로 넘겨주셔야 페이징 흐름에서 일관성있게 응답이 됩니다. ",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = QueryPreferencePoolCarouselResponse.class))),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/home/preference")
	public ResponseEntity<ResponseJsonObject<QueryPreferencePoolCarouselResponse>> getHomePreferencePoolBuddyEvent(
		HttpServletRequest httpServletRequest,
		@RequestParam(value = "eventStartDate") @NotNull @Schema(example = "2024-06-01T00:00:00") LocalDateTime eventStartDate) {
		try {
			// 1. UserID 추출하기
			Long userId = getUserId(httpServletRequest);

			if (userId == null)
				throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

			QueryPreferencePoolCarouselResponse homeWeekly = getBuddyEventCarouselUseCase.getHomePreferencePoolBuddyEvent(
				userId,
				GetHomePreferencePoolBuddyEventCommand.builder()
					.eventStartDate(eventStartDate)
					.build()
			);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<QueryPreferencePoolCarouselResponse> response = new ResponseJsonObject<>(
				ServiceStatusCode.OK, homeWeekly);

			return ResponseEntity.ok(response);

		} catch (BuddyMeException be) {
			throw be;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}

	}

	@Operation(
		summary = "메인 홈 모집 중인 버디 이벤트 조회하기",
		description = "메인 홈 모집 중인 버디 이벤트 조회하기. 메인 홈에서 N명의 다이버가 버디를 찾고있어요 영역 조회. 조회 파라미터중 시작 시간은 1페이지 조회 시작 시간을 다음 페이지 부터 동일 한 시간으로 넘겨주셔야 페이징 흐름에서 일관성있게 응답이 됩니다. ",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BuddyEventCarouselCardResponse.class))),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/home/active")
	public ResponseEntity<ResponseJsonObject<QueryComponentListResponse>> getHomeActiveBuddyEvent(
		@RequestParam(value = "eventStartDate") @NotNull @Schema(example = "2024-06-01T00:00:00") LocalDateTime eventStartDate,
		@RequestParam("pageNumber") @NotNull @Positive @Schema(example = "1") Integer pageNumber,
		@RequestParam("pageSize") @NotNull @Positive @Schema(example = "10") Integer pageSize,
		HttpServletRequest httpServletRequest) {
		try {
			// 1. UserID 추출하기
			Long userId = getUserId(httpServletRequest);

			QueryComponentListResponse activeBuddyEvent = getBuddyEventCarouselUseCase.getHomeActiveBuddyEvent(
				userId,
				GetHomeActiveBuddyEventCommand.builder()
					.eventStartDate(eventStartDate)
					.pageNumber(pageNumber)
					.pageSize(pageSize)
					.build()
			);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<QueryComponentListResponse> response = new ResponseJsonObject<>(
				ServiceStatusCode.OK, activeBuddyEvent);

			return ResponseEntity.ok(response);

		} catch (BuddyMeException be) {
			throw be;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}

	}

}
