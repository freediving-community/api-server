package com.freediving.memberservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.application.port.in.UpdateUserInfoCommand;
import com.freediving.memberservice.application.port.in.UpdateUserUseCase;
import com.freediving.memberservice.application.port.out.UpdateUserPort;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/26
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/26        sasca37       최초 생성
 */
@UseCase
@RequiredArgsConstructor
@Transactional
public class UpdateUserService implements UpdateUserUseCase {

	private final UpdateUserPort updateUserPort;

	@Override
	public FindUserResponse updateUserInfo(UpdateUserInfoCommand command) {
		return updateUserPort.updateUserInfo(command);
	}
}
