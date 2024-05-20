package com.freediving.memberservice.application.port.in;

import java.util.List;

import com.freediving.common.SelfValidating;
import com.freediving.memberservice.domain.DiveType;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/20
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/20        sasca37       최초 생성
 */

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateUserInfoCommandV2 extends SelfValidating<CreateUserInfoCommandV2> {

	private Long userId;
	private DiveType diveType;
	private Integer licenseLevel;
	private String licenseImgUrl;
	private List<String> poolList;
	private List<String> conceptList;

	public CreateUserInfoCommandV2(Long userId, DiveType diveType, Integer licenseLevel, String licenseImgUrl,
		List<String> poolList, List<String> conceptList) {
		this.userId = userId;
		this.diveType = diveType;
		this.licenseLevel = licenseLevel;
		this.licenseImgUrl = licenseImgUrl;
		this.poolList = poolList;
		this.conceptList = conceptList;
		this.validateSelf();
	}
}
