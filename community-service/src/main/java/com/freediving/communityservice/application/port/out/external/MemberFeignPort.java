package com.freediving.communityservice.application.port.out.external;

import java.util.List;
import java.util.Map;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.dto.member.MemberFindUserResponse;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;

public interface MemberFeignPort {

	ResponseJsonObject<List<MemberFindUserResponse>> findUserListByUserIds(List<Long> userIdList, Boolean profileImgTF);

	UserInfo getUserInfoByUserId(Long userId, Boolean profileImgTF);

	Map<Long, UserInfo> getUserMapByUserIds(List<Long> userIds, Boolean profileImgTF);

	void deleteImageByUrls(List<String> deleteImages);
}
