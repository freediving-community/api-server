package com.freediving.communityservice.application.port.out.external;

import java.util.List;

import com.freediving.common.response.dto.member.MemberFindUserResponse;

public interface MemberFeignPort {

	List<MemberFindUserResponse> findUserListByUserIds(List<Long> userIdList, Boolean profileImgTF);
}
