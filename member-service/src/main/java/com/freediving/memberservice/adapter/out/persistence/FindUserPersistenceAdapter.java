package com.freediving.memberservice.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.memberservice.application.port.out.FindUserPort;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.exception.ErrorCode;
import com.freediving.memberservice.exception.MemberServiceException;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class FindUserPersistenceAdapter implements FindUserPort {

	private final UserJpaRepository userJpaRepository;

	@Override
	public User findUserDetailById(Long userId) {
		UserJpaEntity userJpaEntity = userJpaRepository.findUserDetailById(userId)
			.orElseThrow(() -> new MemberServiceException(ErrorCode.NOT_FOUND_USER));

		return User.fromJpaEntityDetail(userJpaEntity);
	}

	@Override
	public List<User> findUserListByIds(List<Long> userIds) {
		List<UserJpaEntity> userJpaEntityList = userJpaRepository.findAllById(userIds);
		return userJpaEntityList.stream().map(User::fromJpaEntityDetail).collect(Collectors.toList());
	}
}
