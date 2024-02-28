package com.freediving.memberservice.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;
import com.freediving.memberservice.application.port.in.FindUserListQuery;
import com.freediving.memberservice.application.port.in.FindUserQuery;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
import com.freediving.memberservice.application.port.out.FindUserPort;
import com.freediving.memberservice.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : UseCase 정보를 기반으로 유저 정보를 조회하는 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindUserService implements FindUserUseCase {

	private final FindUserPort findUserPort;

	@Override
	public User findUserDetailByQuery(FindUserQuery findUserQuery) {
		return findUserPort.findUserDetailById(findUserQuery.userId());
	}

	@Override
	public List<FindUserServiceResponse> findUserListByQuery(FindUserListQuery findUserListQuery) {
		List<User> findUserList = findUserPort.findUserListByIds(findUserListQuery.userIds());
		List<FindUserServiceResponse> findUserServiceResponse = FindUserServiceResponse.of(findUserList,
			findUserListQuery.profileImgTF());

		Map<Long, FindUserServiceResponse> hashMap = new HashMap<>();
		findUserServiceResponse.forEach(r -> hashMap.put(r.getUserId(), r));

		return findUserListQuery.userIds().stream()
			.map(id -> hashMap.getOrDefault(id, FindUserServiceResponse.builder().userId(id).build()))
			.collect(Collectors.toList());
	}

}
