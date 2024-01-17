package com.freediving.memberservice.adapter.out.persistence;

import com.freediving.memberservice.domain.SexType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/16
 * @Description    : User가 직접 입력한 개인정보를 저장하는 VO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/16        sasca37       최초 생성
 */

@Embeddable
public class UserPersonalVO {

	@Enumerated(value = EnumType.STRING)
	@Column(name = "sex_type", nullable = true, length = 10)
	private SexType sexType;

	@Column(name = "phone_number", nullable = true, length = 50)
	private String phoneNumber;
}
