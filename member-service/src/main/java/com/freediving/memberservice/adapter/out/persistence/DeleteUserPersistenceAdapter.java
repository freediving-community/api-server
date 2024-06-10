package com.freediving.memberservice.adapter.out.persistence;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.application.port.out.DeleteUserPort;
import com.freediving.memberservice.domain.UserStatus;
import com.freediving.memberservice.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/31
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/31        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class DeleteUserPersistenceAdapter implements DeleteUserPort {

	private final UserJpaRepository userJpaRepository;

	@Override
	public void deleteUser(Long userId) {
		UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, ErrorCode.NOT_FOUND_USER.getMessage()));
		userJpaEntity.updateStatus(UserStatus.WITHDRAWN);
	}
}
