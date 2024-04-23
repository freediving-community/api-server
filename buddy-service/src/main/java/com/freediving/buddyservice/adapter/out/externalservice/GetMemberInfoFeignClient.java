package com.freediving.buddyservice.adapter.out.externalservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "get/v1/service/users", url = "${feign.external-service.member-service}")
public interface GetMemberInfoFeignClient {
	@GetMapping("/v1/service/users")
	Object getMemberInfo(@RequestParam List<Long> userIds,
		@RequestParam Boolean profileImg);
}
