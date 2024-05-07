package com.freediving.buddyservice.adapter.out.externalservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface GetMemberInfoFeignClient {
	@GetMapping("/v1/internal/users")
	Object getMemberInfo(@RequestParam(name = "userIds") List<Long> userIds,
		@RequestParam(name = "profileImg") Boolean profileImg);
}
