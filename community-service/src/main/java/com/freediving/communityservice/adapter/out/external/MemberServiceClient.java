package com.freediving.communityservice.adapter.out.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freediving.common.response.dto.member.MemberFindUserResponse;

@FeignClient(name = "MEMBER-SERVICE")
public interface MemberServiceClient {

	@GetMapping(path = "v1/internal/users", produces = "application/json")
	List<MemberFindUserResponse> findUserListByUserIds(
		@RequestParam("userIds") List<Long> userIdList,
		@RequestParam("profileImg") Boolean profileImgTF
	);
}
