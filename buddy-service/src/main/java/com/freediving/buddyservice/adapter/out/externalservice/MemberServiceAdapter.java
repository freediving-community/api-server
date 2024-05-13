package com.freediving.buddyservice.adapter.out.externalservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.freediving.buddyservice.application.port.out.externalservice.query.RequestMemberPort;
import com.freediving.common.domain.member.FreeDiving;
import com.freediving.common.domain.member.RoleLevel;
import com.freediving.common.response.ResponseJsonObject;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberServiceAdapter implements RequestMemberPort {

	private final GetMemberInfoFeignClient getMemberInfoFeignClient;

	//TODO 멤버 유효성 요청 구현 필요.
	@Override
	public HashMap<Long, FindUser> getMemberStatus(List<Long> userId) {

		try {
			ResponseJsonObject<List<FindUser>> obj = getMemberInfoFeignClient.getMemberInfo(userId, true);
			List<FindUser> users = obj.getData();
			HashMap<Long, FindUser> response = new HashMap<>();

			users.stream().forEach(
				e -> response.put(e.getUserId(), e)
			);

			return response;
		} catch (Exception e) {

			//todo 임시로 연결안되면 랜덤 사용자로 리턴

			HashMap<Long, FindUser> response = new HashMap<>();

			for (Long id : userId) {
				response.put(id,
					FindUser.builder().userId(id).nickname("임시 사용자-" + id).licenseInfo(LicenseInfo.builder()
						.freeDiving(
							new FreeDiving(RoleLevel.UNREGISTER.getLevel(), RoleLevel.UNREGISTER.name(), null, "",
								false))
						.build()).build());

			}
			return response;
		}

	}
}
