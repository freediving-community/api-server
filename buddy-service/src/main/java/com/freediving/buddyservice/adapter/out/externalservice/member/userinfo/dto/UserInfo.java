package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.freediving.buddyservice.config.enumerate.UserStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "콘텐츠 유저 정보", description = "콘텐츠(버디 모임, 게시글 등)의 작성자 유저 정보")
public class UserInfo {

	private Long userId;

	private String profileImgUrl;

	private String nickname;
	@JsonDeserialize
	@JsonIgnore
	private UserStatus userStatus;
	private LicenseInfo licenseInfo;

}
