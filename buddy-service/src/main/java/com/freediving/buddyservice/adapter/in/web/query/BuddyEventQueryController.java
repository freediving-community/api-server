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
import com.freediving.buddyservice.application.port.in.web.query.carouselsimple.GetBuddyEventCarouselSimpleCommand;
import com.freediving.buddyservice.application.port.in.web.query.carouselsimple.GetBuddyEventCarouselSimpleUseCase;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingUseCase;
import com.freediving.buddyservice.domain.query.QueryBuddyEventDetailResponse;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.buddyservice.domain.query.QueryComponentListWithoutPageResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventCarouselSimpleCardResponse;
import com.freediving.buddyservice.domain.query.component.BuddyEventlistingCardResponse;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
@Tag(name = "Buddy Event Query", description = "버디 이벤트 Query 관련 API")
public class BuddyEventQueryController {

	private final GetBuddyEventListingUseCase getBuddyEventListingUseCase;
	private final BuddyEventDetailUseCase buddyEventDetailUseCase;
	private final GetBuddyEventCarouselSimpleUseCase getBuddyEventCarouselSimpleUseCase;

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
		description = "버디 이벤트 상세정보를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 매칭 조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = QueryBuddyEventDetailResponse.class))
			),
			@ApiResponse(responseCode = "204", description = "존재하지 않는 버디 모임 상세정보 또는 잘못된 사용자의 버디 모임  ", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", description = "서비스 에러", ref = "#/components/responses/500")
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
			@ApiResponse(responseCode = "204", description = "조회 결과 없음", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", description = "서비스 에러", ref = "#/components/responses/500")
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
		summary = "캐로셀 심플 카드 버디 모임 조회하기.",
		description = "캐로셀 심플 카드 유형으로 버디 모임을 조회합니다.  "
			+ "<br> 버디 모임 상세 정보 하단의 캐로셀 심플 카드 유형의 조회에 사용됩니다."
			+ "<br> 1일 단위의 조회는 divingPool은 null, 현재 시간부터 다음날 0시로 조회합니다."
			+ "<br> 다이빙 풀 조회는 적절한 시간 세팅 후 조회하시면 됩니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = BuddyEventCarouselSimpleCardResponse.class))),
			@ApiResponse(responseCode = "204", description = "조회 결과 없음", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", description = "서비스 에러", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/event/simple")
	public ResponseEntity<ResponseJsonObject<QueryComponentListWithoutPageResponse>> getBuddyEventCarouselSimple(
		@RequestParam(value = "eventStartDate") @Valid @NotNull @Schema(example = "2024-06-01T00:00:00") LocalDateTime eventStartDate,
		@RequestParam(value = "eventEndDate") @Valid @NotNull @Schema(example = "2024-06-02T00:00:00") LocalDateTime eventEndDate,
		@RequestParam(value = "divingPool", required = false) @Parameter(schema = @Schema(implementation = DivingPool.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED)) DivingPool divingPool,
		@RequestParam(value = "excludedEventId", required = false) @Schema(description = "제외할 Event ID", example = "12345", requiredMode = Schema.RequiredMode.NOT_REQUIRED) Long excludedEventId,
		HttpServletRequest httpServletRequest) {
		try {

			QueryComponentListWithoutPageResponse buddyEvent = getBuddyEventCarouselSimpleUseCase.getBuddyEventCarouselSimple(
				GetBuddyEventCarouselSimpleCommand.builder()
					.eventStartDate(eventStartDate)
					.eventEndDate(eventEndDate)
					.divingPool(divingPool)
					.excludedEventId(excludedEventId)
					.build()
			);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<QueryComponentListWithoutPageResponse> response = new ResponseJsonObject<>(
				ServiceStatusCode.OK, buddyEvent);

			return ResponseEntity.ok(response);

		} catch (BuddyMeException be) {
			throw be;
		} catch (Exception e) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, e.getMessage());
		}

	}
}
