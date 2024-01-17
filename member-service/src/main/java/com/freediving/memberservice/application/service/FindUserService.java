package com.freediving.memberservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.application.port.in.FindUserQuery;
import com.freediving.memberservice.application.port.in.FindUserUseCase;
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

	// private final FindUserPort findUserPort;

	@Override
	public User findUser(FindUserQuery findUserQuery) {
		// User user = findUserPort.findUser(findUserCommand.getUserId());
		// if (user == null)
		return null;
	}
}
