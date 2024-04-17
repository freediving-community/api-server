package com.freediving.buddyservice.adapter.out.externalservice;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.application.port.out.externalservice.query.MemberStatus;
import com.freediving.buddyservice.application.port.out.externalservice.query.RequestMemberPort;

@Component
public class MemberServiceAdapter implements RequestMemberPort {

	//TODO 멤버 유효성 요청 구현 필요.
	@Override
	public MemberStatus getMemberStatus(Long userId) {
		return MemberStatus.builder().userid(userId).isValid(true).build();
	}
}
