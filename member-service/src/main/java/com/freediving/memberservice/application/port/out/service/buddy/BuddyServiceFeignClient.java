package com.freediving.memberservice.application.port.out.service.buddy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.memberservice.adapter.out.dto.UserConceptResponse;
import com.freediving.memberservice.adapter.out.dto.UserPoolResponse;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : BuddyService 통신 작업 매핑
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */

@FeignClient(name = "BUDDY-SERVICE")
public interface BuddyServiceFeignClient {

	@GetMapping(path = "/v1/internal/{userId}/pool", produces = "application/json")
	ResponseEntity<ResponseJsonObject<UserPoolResponse>> userDivingPoolListByUserId(@PathVariable("userId") Long userId);

	@GetMapping(path = "/v1/internal/{userId}/concept", produces = "application/json")
	ResponseEntity<ResponseJsonObject<UserConceptResponse>> userConceptListByUserId(@PathVariable("userId") Long userId);
}
