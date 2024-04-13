package com.freediving.memberservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.application.port.in.DeleteUserUseCase;
import com.freediving.memberservice.application.port.out.DeleteUserPort;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/31
 * @Description    : UseCase 정보를 기반으로 유저 정보를 삭제하는 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/31        sasca37       최초 생성
 */

@UseCase
@RequiredArgsConstructor
@Transactional
public class DeleteUserService implements DeleteUserUseCase {

	private final DeleteUserPort deleteUserPort;

	@Override
	public void deleteUser(Long userId) {
		deleteUserPort.deleteUser(userId);
	}
}
