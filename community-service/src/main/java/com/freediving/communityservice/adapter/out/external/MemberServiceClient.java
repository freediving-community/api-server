package com.freediving.communityservice.adapter.out.external;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.dto.member.MemberFindUserResponse;

@FeignClient(name = "MEMBER-SERVICE")
public interface MemberServiceClient {

	@GetMapping(path = "v1/internal/users", produces = "application/json")
	ResponseJsonObject<List<MemberFindUserResponse>> findUserListByUserIds(
		@RequestParam("userIds") List<Long> userIdList,
		@RequestParam("profileImg") Boolean profileImgTF
	);

	@DeleteMapping(path = "v1/internal/imgs")
	ResponseEntity<Object> deleteImages(@RequestBody Map<String, Object> deleteImages);
}
