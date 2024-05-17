package com.freediving.buddyservice.application.port.out.externalservice.query;

import java.util.HashMap;
import java.util.List;

import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.UserInfo;

public interface RequestMemberPort {

	public HashMap<Long, UserInfo> getMemberStatus(List<Long> userId);
}
