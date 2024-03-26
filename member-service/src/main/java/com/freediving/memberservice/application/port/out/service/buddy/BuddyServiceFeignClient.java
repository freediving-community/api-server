package com.freediving.memberservice.application.port.out.service.buddy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.freediving.common.response.ResponseJsonObject;

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

	@GetMapping(path = "/v1/pool", produces = "application/json")
	ResponseEntity<ResponseJsonObject<?>> getDivingPools();
}
