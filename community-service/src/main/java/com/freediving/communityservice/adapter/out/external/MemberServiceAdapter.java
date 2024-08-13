package com.freediving.communityservice.adapter.out.external;

import static com.freediving.common.response.enumerate.ServiceStatusCode.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.freediving.common.config.annotation.ExternalSystemAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.dto.member.MemberFindUserResponse;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;
import com.freediving.communityservice.application.port.out.external.MemberFeignPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ExternalSystemAdapter
public class MemberServiceAdapter implements MemberFeignPort {

	private final MemberServiceClient memberClient;

	@Override
	public ResponseJsonObject<List<MemberFindUserResponse>> findUserListByUserIds(List<Long> userIdList,
		Boolean profileImgTF) {
		return memberClient.findUserListByUserIds(userIdList, profileImgTF);
	}

	public UserInfo getUserInfoByUserId(Long userId, Boolean profileImgTF) {
		Map<Long, UserInfo> userInfoMap = this.getUserMapByUserIds(List.of(userId), profileImgTF);
		if (userInfoMap.isEmpty()) {
			throw new BuddyMeException(INTERVAL_SERVER_ERROR, "사용자 조회에 실패했습니다.");
		}
		return userInfoMap.get(userId);
	}

	public Map<Long, UserInfo> getUserMapByUserIds(List<Long> userIds, Boolean profileImgTF) {

		if (userIds.isEmpty()) {
			return new HashMap<Long, UserInfo>();
		}

		ResponseJsonObject<List<MemberFindUserResponse>> memberInfoList = findUserListByUserIds(userIds, profileImgTF);
		if (ServiceStatusCode.OK.getCode() != memberInfoList.getCode()) {
			throw new BuddyMeException(INTERVAL_SERVER_ERROR, "사용자 조회에 실패했습니다.");
		}

		return memberInfoList.getData()
			.stream()
			.collect(Collectors.toUnmodifiableMap(
				MemberFindUserResponse::getUserId,
				m -> new UserInfo(m.getUserId(), m.getUserStatus(), m.getNickname(), m.getProfileImgUrl(),
					m.getLicenseInfo())
			));
	}

	@Override
	public void deleteImageByUrls(List<String> deleteImages) {
		try {
			ResponseEntity<Object> response = memberClient.deleteImages(Map.of("imgUrlList", deleteImages));
			if (response.getStatusCode().value() > HttpStatus.NO_CONTENT.value()) {
				throw new BuddyMeException(INTERVAL_SERVER_ERROR, "이미지 삭제 요청이 실패하였습니다.");
			}
		} catch (Exception ex) {
			//TODO: 관리자 모니터링 데이터 삽입 , 알림 , 재처리 시도 등
			log.error("MemberService로 AWS 이미지 삭제 요청이 실패하였습니다.", ex);
		}
	}
}
