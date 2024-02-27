package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/26
 * @Description    : 유저 조회 요청에 대한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/26        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FindUserServiceResponse {

	private Long userId;

	private String profileImgUrl;

	private String nickname;

	private Integer roleLevel;

	private String roleLevelCode;

	private Integer licenceLevel;

	private String licenceImgUrl;

	private Boolean licenceValidTF;

	public static List<FindUserServiceResponse> of(List<User> findUserList, Boolean profileImgTF) {
		return findUserList.stream().map(e -> of(e, profileImgTF)).collect(Collectors.toList());
	}

	private static FindUserServiceResponse of(User user, Boolean profileImgTF) {
		UserLicence userLicence = Optional.ofNullable(user.userLicence()).orElse(null);
		return FindUserServiceResponse.builder()
			.userId(user.userId())
			.profileImgUrl(profileImgTF == true ? user.profileImgUrl() : null)
			.nickname(user.nickname())
			.roleLevel(user.roleLevel().getLevel())
			.roleLevelCode(user.roleLevel().name())
			.licenceLevel(!ObjectUtils.isEmpty(userLicence) ? userLicence.licenceLevel() : null)
			.licenceImgUrl(!ObjectUtils.isEmpty(userLicence) ? userLicence.licenceImgUrl() : null)
			.licenceValidTF(!ObjectUtils.isEmpty(userLicence) ? userLicence.confirmTF() : null)
			.build();
	}
}
