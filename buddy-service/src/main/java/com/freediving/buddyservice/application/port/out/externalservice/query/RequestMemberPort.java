package com.freediving.buddyservice.application.port.out.externalservice.query;

import java.util.HashMap;
import java.util.List;

import com.freediving.buddyservice.adapter.out.externalservice.FindUser;

public interface RequestMemberPort {

	public HashMap<Long, FindUser> getMemberStatus(List<Long> userId);
}
