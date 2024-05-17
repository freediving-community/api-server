package com.freediving.buddyservice.adapter.in.externalservice.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.adapter.out.externalservice.opendata.HolidayService;
import com.freediving.buddyservice.application.port.in.externalservice.query.GetBuddyEventConceptListUseCase;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventConceptListResponse;
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
@RequestMapping("/v1/attribute")
@Tag(name = "Attribute", description = "버디 이벤트 속성 API")
public class BuddyEventAttributeController {

	private final GetBuddyEventConceptListUseCase getBuddyEventConceptListUseCase;

	@Autowired
	private HolidayService holidayService;

	@Operation(
		summary = "공휴일 조회하기",
		description = "현재, 다음, 다다음 달의 공휴일을 조회합니다. 리턴 방식은 공공데이터와 동일합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(
					mediaType = "application/xml",
					schema = @Schema(implementation = String.class, example =
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
							+ "<response>\n"
							+ "  <body>\n"
							+ "    <items>\n"
							+ "      <item>\n"
							+ "        <dateKind>01</dateKind>\n"
							+ "        <dateName>어린이날</dateName>\n"
							+ "        <isHoliday>Y</isHoliday>\n"
							+ "        <locdate>20240505</locdate>\n"
							+ "        <seq>1</seq>\n"
							+ "      </item>\n"
							+ "    </items>\n"
							+ "  </body>\n"
							+ "</response>\n")
				)
			),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping("/holidays")
	public String getHolidays() {
		return holidayService.getCachedXml();
	}

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
