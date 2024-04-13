package com.freediving.buddyservice.application.port.out.externalservice.query;

public interface RequestMemberPort {

	public MemberStatus getMemberStatus(Long userId);
}
