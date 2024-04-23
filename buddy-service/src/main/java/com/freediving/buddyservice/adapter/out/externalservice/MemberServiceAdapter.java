package com.freediving.buddyservice.adapter.out.externalservice;

import java.util.List;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.application.port.out.externalservice.query.MemberStatus;
import com.freediving.buddyservice.application.port.out.externalservice.query.RequestMemberPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberServiceAdapter implements RequestMemberPort {

	private final GetMemberInfoFeignClient getMemberInfoFeignClient;

	//TODO 멤버 유효성 요청 구현 필요.
	@Override
	public MemberStatus getMemberStatus(Long userId) {

		getMemberInfoFeignClient.getMemberInfo(List.of(userId), false);
		return MemberStatus.builder().userid(userId).isValid(true).build();
	}
}
