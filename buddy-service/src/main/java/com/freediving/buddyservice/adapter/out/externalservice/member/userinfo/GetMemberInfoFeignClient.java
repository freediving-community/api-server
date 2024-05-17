package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.UserInfo;
import com.freediving.buddyservice.config.FeignClientConfiguration;
import com.freediving.common.response.ResponseJsonObject;

@FeignClient(name = "member-service", configuration = FeignClientConfiguration.class)
public interface GetMemberInfoFeignClient {
	@GetMapping("/v1/internal/users")
	ResponseJsonObject<List<UserInfo>> getMemberInfo(@RequestParam(name = "userIds") List<Long> userIds,
		@RequestParam(name = "profileImg") Boolean profileImg);
}
