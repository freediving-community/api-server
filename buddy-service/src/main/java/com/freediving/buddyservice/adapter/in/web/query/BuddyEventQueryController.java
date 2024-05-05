package com.freediving.buddyservice.adapter.in.web.query;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.adapter.in.web.query.dto.GetBuddyEventListingRequest;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingCommand;
import com.freediving.buddyservice.application.port.in.web.query.listing.GetBuddyEventListingUseCase;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.buddyservice.domain.query.QueryComponentListResponse;
import com.freediving.common.config.annotation.WebAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/event")
@Tag(name = "Buddy Event Query", description = "버디 이벤트 Query 관련 API")
public class BuddyEventQueryController {

	private GetBuddyEventListingUseCase getBuddyEventListingUseCase;

	/* API  캐로셀 카드 조회 하기.
	*   - 메인 홈 N명의 다이버가 버디를 찾고 있어요.
	*   - 메인 홈 이번주에 프리다이빙 어때요?
	*   - 메인 홈 [DivingPool Name]에 같이 갈래요?
	*   -
	*/


	// API 캐로셀 심플 카드 조회 하기.


	// API 리스팅 카드 조회 하기.

	@Operation(
		summary = "버디 이벤트 버디 매칭 조회 하기 ",
		description = "버디 매칭을 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "버디 매칭 조회 성공",
				useReturnTypeSchema = true
			),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "400", ref = "#/components/responses/400"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/listing")
	public ResponseEntity<ResponseJsonObject<QueryComponentListResponse>> getBuddyEventListing(@Valid @RequestBody
	GetBuddyEventListingRequest request, HttpServletRequest httpServletRequest){
		try {
			// 1. UserID 추출하기
			Long userId = Long.parseLong(httpServletRequest.getAttribute("User-Id").toString());

			if (userId == null)
				throw new BuddyMeException(ServiceStatusCode.UNAUTHORIZED);

			QueryComponentListResponse buddyEventListingResponse = getBuddyEventListingUseCase.getBuddyEventListing(
				GetBuddyEventListingCommand.builder()
					.eventStartDate(request.getEventStartDate())
					.eventEndDate(request.getEventEndDate())
					.buddyEventConcepts(request.getBuddyEventConcepts())
					.carShareYn(request.getCarShareYn())
					.freedivingLevel(request.getFreedivingLevel())
					.divingPools(request.getDivingPools())
					.sortType(request.getSortType())
					.build()
			);

			// 3. Command 요청 및 응답 리턴.
			ResponseJsonObject<QueryComponentListResponse> response = new ResponseJsonObject<>(ServiceStatusCode.OK,
				buddyEventListingResponse);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw e;
		}
	}


}
