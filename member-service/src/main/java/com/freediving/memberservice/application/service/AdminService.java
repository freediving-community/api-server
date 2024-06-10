package com.freediving.memberservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.adapter.in.web.dto.FindUserLicenseResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.application.port.in.AdminUseCase;
import com.freediving.memberservice.application.port.in.UpdateUserInfoCommand;
import com.freediving.memberservice.application.port.in.UpdateUserLicenseStatusCommand;
import com.freediving.memberservice.application.port.in.UpdateUserUseCase;
import com.freediving.memberservice.application.port.out.AdminPort;
import com.freediving.memberservice.application.port.out.UpdateUserPort;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */
@UseCase
@RequiredArgsConstructor
@Transactional
public class AdminService implements AdminUseCase {

	private final AdminPort adminPort;

	@Override
	public FindUserLicenseResponse findUserLicenseSubmitList() {
		return adminPort.findUserLicenseSubmitList();
	}

	@Override
	public void updateUserLicenseStatus(UpdateUserLicenseStatusCommand command) {
		adminPort.updateUserLicenseStatus(command);
	}
}
