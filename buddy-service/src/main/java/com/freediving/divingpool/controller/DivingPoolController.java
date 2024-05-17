package com.freediving.divingpool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.divingpool.config.enumerate.DetailLevel;
import com.freediving.divingpool.data.dto.DivingPoolListResponse;
import com.freediving.divingpool.data.dto.DivingPoolResponse;
import com.freediving.divingpool.data.dto.DivingPoolSimpleResponse;
import com.freediving.divingpool.service.DivingPoolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/pool")
@Tag(name = "Diving Pool", description = "다이빙 풀 관련 API")
public class DivingPoolController {

	private final DivingPoolService divingPoolService;

	@Operation(
		summary = "모든 다이빙 풀 조회",
		description = "노출 중인 모든 다이빙 풀을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "노출 중인 모든 다이빙 풀 조회 성공 ",
			content = @Content(mediaType = "application/json",
				schema = @Schema(oneOf = {DivingPoolResponse.class, DivingPoolSimpleResponse.class})
			)
		),
		@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
		@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
	})
	@GetMapping()
	public ResponseEntity<ResponseJsonObject<DivingPoolListResponse>> findByAllDivingPool(
		@Schema(description = "디테일 강도", implementation = DetailLevel.class)
		@RequestParam(value = "detail") @Valid @NotNull DetailLevel detail) {

		DivingPoolListResponse divingPoolListResponse = divingPoolService.findByAllDivingPool(detail);

		ResponseJsonObject responseJsonObject = ResponseJsonObject.builder()
			.code(ServiceStatusCode.OK)
			.data(divingPoolListResponse)
			.build();

		return ResponseEntity.ok(responseJsonObject);

	}

}
