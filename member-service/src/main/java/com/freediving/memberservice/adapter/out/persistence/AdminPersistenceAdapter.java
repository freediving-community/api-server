package com.freediving.memberservice.adapter.out.persistence;

import java.util.List;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.FindUserLicense;
import com.freediving.memberservice.adapter.in.web.dto.FindUserLicenseResponse;
import com.freediving.memberservice.application.port.in.UpdateUserLicenseStatusCommand;
import com.freediving.memberservice.application.port.out.AdminPort;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/26
 * @Description    : 유저 정보 수정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/26        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class AdminPersistenceAdapter implements AdminPort {

	private final UserJpaRepository userJpaRepository;
	private final UserLicenseJpaRepository userLicenseJpaRepository;

	@Override
	public FindUserLicenseResponse findUserLicenseSubmitList() {
		List<UserLicenseJpaEntity> userLicenseJpaList = userLicenseJpaRepository.findUserLicenseSubmitList();
		List<FindUserLicense> findUserLicenseList = userLicenseJpaList.stream()
			.map(FindUserLicense::fromUserLicenseJpaEntity)
			.toList();
		return FindUserLicenseResponse.builder().licenseList(findUserLicenseList).build();
	}

	@Override
	public void updateUserLicenseStatus(UpdateUserLicenseStatusCommand command) {
		UserLicenseJpaEntity userLicenseJpaEntity = userLicenseJpaRepository.findById(command.getLicenseId())
			.orElseThrow(() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "라이센스 식별 값 정보가 존재하지 않습니다."));

		userLicenseJpaEntity.updateLicenseStatusInfo(command.getOrgName(), command.getLicenseLevel(),
			command.getConfirmTF(), command.getAdminUserId());

		// TODO : 승인 / 거절에 대한 알림 카프카 전송
	}
}
