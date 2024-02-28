package com.freediving.memberservice.fixture;

import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;

/**
 * @Author         : sasca37
 * @Date           : 2/28/24
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2/28/24        sasca37       최초 생성
 */
public class FindUserServiceResponseFixture {

	public static FindUserServiceResponse createFindUserServiceResponseByUserId(Long userId) {
		return FindUserServiceResponse.builder()
			.userId(userId)
			.build();
	}
}
