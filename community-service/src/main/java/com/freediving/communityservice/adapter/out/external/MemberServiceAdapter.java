package com.freediving.communityservice.adapter.out.external;

import java.util.List;

import com.freediving.common.config.annotation.ExternalSystemAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.dto.member.MemberFindUserResponse;
import com.freediving.communityservice.application.port.out.external.MemberFeignPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ExternalSystemAdapter
public class MemberServiceAdapter implements MemberFeignPort {

	private final MemberServiceClient memberClient;

	@Override
	public ResponseJsonObject<List<MemberFindUserResponse>> findUserListByUserIds(
		List<Long> userIdList,
		Boolean profileImgTF
	) {
		return memberClient.findUserListByUserIds(userIdList, profileImgTF);
	}
}
