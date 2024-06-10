package com.freediving.buddyservice.adapter.out.externalservice.member.userinfo;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.FreeDiving;
import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.LicenseInfo;
import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.ScubaDiving;
import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.UserInfo;
import com.freediving.buddyservice.application.port.out.Internalservice.query.RequestMemberPort;
import com.freediving.buddyservice.config.enumerate.UserStatus;
import com.freediving.common.domain.member.RoleLevel;
import com.freediving.common.response.ResponseJsonObject;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberServiceAdapter implements RequestMemberPort {

	private final GetMemberInfoFeignClient getMemberInfoFeignClient;

	@Override
	public HashMap<Long, UserInfo> getMemberStatus(List<Long> userId) {

		try {
			ResponseJsonObject<List<UserInfo>> obj = getMemberInfoFeignClient.getMemberInfo(userId, true);
			List<UserInfo> users = obj.getData();
			HashMap<Long, UserInfo> response = new HashMap<>();

			users.stream().filter(e -> (e != null && e.getUserStatus().equals(UserStatus.ACTIVE))).forEach(
				e -> response.put(e.getUserId(), e)
			);

			return response;
		} catch (Exception e) {

			//todo 임시로 연결안되면 랜덤 사용자로 리턴

			HashMap<Long, UserInfo> response = new HashMap<>();

			for (Long id : userId) {
				response.put(id,
					UserInfo.builder().userId(id).nickname("임시 사용자-" + id).licenseInfo(LicenseInfo.builder()
						.freeDiving(
							new FreeDiving(RoleLevel.UNREGISTER.getLevel(), RoleLevel.UNREGISTER.name(), null,
								false))
						.scubaDiving(new ScubaDiving(RoleLevel.UNREGISTER.getLevel(), RoleLevel.UNREGISTER.name(), null,
							false))
						.build()).build());

			}
			return response;
		}

	}
}
