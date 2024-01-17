package com.freediving.memberservice.adapter.out.persistence;

import java.util.HashSet;
import java.util.Set;

import com.freediving.memberservice.domain.OauthType;

import lombok.Value;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : 소셜 로그인 연동 정보를 저장하는 JPA Value Object
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */

@Value
public class OauthTypeSetVO {

	private Set<OauthType> oauthTypeSet = new HashSet<>();

	public OauthTypeSetVO(Set<OauthType> oauthTypeSet) {
		this.oauthTypeSet.addAll(oauthTypeSet);
	}
}
