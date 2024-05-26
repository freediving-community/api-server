package com.freediving.memberservice.application.port.in;

import java.util.List;

import com.freediving.common.SelfValidating;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.valid.EnumValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/26
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/26        sasca37       최초 생성
 */
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateUserInfoCommand extends SelfValidating<UpdateUserInfoCommand> {

	private Long userId;
	private DiveType diveType;
	private Integer licenseLevel;
	private String licenseImgUrl;
	private List<String> poolList;
	private List<String> conceptList;
	private String profileImgUrl;
	private String nickname;
	private String content;


	public UpdateUserInfoCommand(Long userId, DiveType diveType, Integer licenseLevel, String licenseImgUrl,
		List<String> poolList,
		List<String> conceptList, String profileImgUrl, String nickname, String content) {
		this.userId = userId;
		this.diveType = diveType;
		this.licenseLevel = licenseLevel;
		this.licenseImgUrl = licenseImgUrl;
		this.poolList = poolList;
		this.conceptList = conceptList;
		this.profileImgUrl = profileImgUrl;
		this.nickname = nickname;
		this.content = content;
		this.validateSelf();
	}
}
