package com.freediving.memberservice.adapter.out.persistence;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.domain.member.RoleLevel;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.adapter.out.dto.UserConceptRequest;
import com.freediving.memberservice.adapter.out.dto.UserPoolRequest;
import com.freediving.memberservice.adapter.out.kafka.UserPreferencesProducer;
import com.freediving.memberservice.application.port.in.UpdateUserInfoCommand;
import com.freediving.memberservice.application.port.out.UpdateUserPort;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.exception.ErrorCode;
import com.freediving.memberservice.exception.MemberServiceException;

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
public class UpdateUserPersistenceAdapter implements UpdateUserPort {
	private final UserJpaRepository userJpaRepository;
	private final UserLicenseJpaRepository userLicenseJpaRepository;
	private final UserPreferencesProducer preferencesProducer;


	@Override
	public FindUserResponse updateUserInfo(UpdateUserInfoCommand command) {
		Long userId = command.getUserId();
		DiveType diveType = command.getDiveType();
		List<String> poolList = command.getPoolList();
		List<String> conceptList = command.getConceptList();

		UserJpaEntity userJpaEntity = getUserJpaEntity(userId);
		List<UserLicenseJpaEntity> userLicenseJpaEntityList = getUserLicenseJpaEntityList(userJpaEntity);

		updateLicenseInfo(diveType, command, userLicenseJpaEntityList);
		updateUserInfo(command, userJpaEntity);
		updateUserPreferences(userId, poolList, conceptList);

		User user = User.fromJpaEntityList(userJpaEntity, userLicenseJpaEntityList);
		return FindUserResponse.from(user);
	}

	private UserJpaEntity getUserJpaEntity(Long userId) {
		return userJpaRepository.findUserDetailById(userId)
			.orElseThrow(() -> new MemberServiceException(ErrorCode.NOT_FOUND_USER));
	}

	private List<UserLicenseJpaEntity> getUserLicenseJpaEntityList(UserJpaEntity userJpaEntity) {
		return userLicenseJpaRepository.findAllByUserJpaEntity(userJpaEntity);
	}

	private void updateLicenseInfo(DiveType diveType, UpdateUserInfoCommand command, List<UserLicenseJpaEntity> userLicenseJpaEntityList) {
		UserLicenseJpaEntity license = findLicenseByDiveType(diveType, userLicenseJpaEntityList);
		license.updateLicenseLevel(command.getLicenseLevel());
		license.updateLicenseImgUrl(command.getLicenseImgUrl());
		if (license.getLicenseLevel() != command.getLicenseLevel()) {
			license.updateRoleLevel(command.getLicenseLevel() == 0 ? RoleLevel.NO_LEVEL : RoleLevel.WAIT_LICENSE_APPROVAL);
		}
	}

	private UserLicenseJpaEntity findLicenseByDiveType(DiveType diveType, List<UserLicenseJpaEntity> userLicenseJpaEntityList) {
		return userLicenseJpaEntityList.stream()
			.filter(x -> x.getDiveType().equals(diveType))
			.findAny()
			.orElseThrow(() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, diveType.name() + " 라이센스 정보가 없습니다."));
	}

	private void updateUserInfo(UpdateUserInfoCommand command, UserJpaEntity userJpaEntity) {
		if (!StringUtils.equals(command.getNickname(), userJpaEntity.getNickname()) && userJpaRepository.findByNickname(command.getNickname()).isPresent()) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "이미 사용중인 닉네임 입니다.");
		}
		userJpaEntity.updateProfileImgUrl(command.getProfileImgUrl());
		userJpaEntity.updateUserNickname(command.getNickname());
		userJpaEntity.updateUserContent(command.getContent());
	}

	private void updateUserPreferences(Long userId, List<String> poolList, List<String> conceptList) {
		UserConceptRequest userConceptRequest = UserConceptRequest.builder()
			.userId(userId)
			.preferredConcepts(conceptList)
			.build();
		preferencesProducer.sendUserConcept(userConceptRequest);

		UserPoolRequest userPoolRequest = UserPoolRequest.builder()
			.userId(userId)
			.preferredPools(poolList)
			.build();
		preferencesProducer.sendUserPool(userPoolRequest);
	}

}
