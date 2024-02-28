package com.freediving.buddyservice.divingpool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.divingpool.data.dto.DivingPoolListResponse;
import com.freediving.buddyservice.divingpool.service.DivingPoolService;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/pool")
@Tag(name = "Diving Pool", description = "다이빙 풀 관련 API")
public class DivingPoolController {

	private final DivingPoolService divingPoolService;

	@Operation(
		summary = "모든 다이빙 풀 조회",
		description = "노출 중인 모든 다이빙 풀을 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "노출 중인 모든 다이빙 풀 조회 성공",
				useReturnTypeSchema = true
			),
			@ApiResponse(responseCode = "204", ref = "#/components/responses/204"),
			@ApiResponse(responseCode = "500", ref = "#/components/responses/500")
		}
	)
	@GetMapping()
	public ResponseEntity<ResponseJsonObject<DivingPoolListResponse>> findByAllDivingPool() {

		DivingPoolListResponse divingPoolListResponse = divingPoolService.findByAllDivingPool();

		ResponseJsonObject responseJsonObject = ResponseJsonObject.builder()
			.code(ServiceStatusCode.OK)
			.data(divingPoolListResponse)
			.build();

		return ResponseEntity.ok(responseJsonObject);

	}

}
