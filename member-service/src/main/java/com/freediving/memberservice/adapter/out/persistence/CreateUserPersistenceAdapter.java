package com.freediving.memberservice.adapter.out.persistence;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.domain.member.RoleLevel;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommand;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.util.NicknameGenerator;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 가입 여부를 확인하고 유저 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateUserPersistenceAdapter implements CreateUserPort {
	private final UserJpaRepository userJpaRepository;
	private final UserLicenseJpaRepository userLicenseJpaRepository;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/17
	 * @Param            : CreateUserCommand
	 * @Return           : User 도메인
	 * @Description      : 최초 로그인인 경우 회원가입 / 기존 가입자인 경우 사용자 정보 반환
	 */
	@Override
	public CreateUserResponse createOrGetUser(CreateUserCommand createUserCommand) {
		final OauthType oauthType = createUserCommand.getOauthType();
		final String email = createUserCommand.getEmail();
		final String profileImgUrl = createUserCommand.getProfileImgUrl();

		UserJpaEntity userJpaEntity = userJpaRepository.findByOauthTypeAndEmail(oauthType, email).orElse(null);

		// 최초 로그인인 경우
		if (ObjectUtils.isEmpty(userJpaEntity)) {
			UserJpaEntity createUserJpaEntity = UserJpaEntity.createSimpleUser(oauthType, email, profileImgUrl);
			UserJpaEntity savedUserJpaEntity = userJpaRepository.save(createUserJpaEntity);
			String randomNickname = NicknameGenerator.generateNickname(savedUserJpaEntity.getUserId());
			savedUserJpaEntity.updateUserNickname(randomNickname);
			UserLicenseJpaEntity freeDivingLicense = UserLicenseJpaEntity.createUserLicenseJpaEntity(savedUserJpaEntity,
				DiveType.FREE_DIVE);
			UserLicenseJpaEntity scubaDivingLicense = UserLicenseJpaEntity.createUserLicenseJpaEntity(
				savedUserJpaEntity,
				DiveType.SCUBA_DIVE);
			userLicenseJpaRepository.save(freeDivingLicense);
			userLicenseJpaRepository.save(scubaDivingLicense);

			List<UserLicenseJpaEntity> userLicenceJpaList = List.of(freeDivingLicense, scubaDivingLicense);

			User user = User.fromJpaEntityList(savedUserJpaEntity, userLicenceJpaList);
			return CreateUserResponse.from(user, true);
		}

		// 기존 가입자인 경우
		List<UserLicenseJpaEntity> userLicenceJpaList = userLicenseJpaRepository.findAllById(
			Collections.singleton(userJpaEntity.getUserId()));
		User user = User.fromJpaEntityList(userJpaEntity, userLicenceJpaList);
		return CreateUserResponse.from(user, false);
	}

	@Override
	public void createUserInfo(CreateUserInfoCommand command) {
		Long userId = command.getUserId();
		DiveType diveType = command.getDiveType();
		UserLicenseJpaEntity userLicenseJpaEntity = userLicenseJpaRepository.findByUserIdAndDiveType(userId, diveType);
		userLicenseJpaEntity.updateLicenseImgUrl(command.getLicenseImgUrl());
		userLicenseJpaEntity.updateLicenseLevel(command.getLicenseLevel());
		// 0레벨인 경우 심사 없이 바로 승인 나머지는 심사중 상태로 변경
		if (command.getLicenseLevel() == 0) {
			userLicenseJpaEntity.updateRoleLevel(RoleLevel.NO_LEVEL);
		} else {
			userLicenseJpaEntity.updateRoleLevel(RoleLevel.WAIT_LICENSE_APPROVAL);
		}

		UserJpaEntity userJpaEntity = userLicenseJpaEntity.getUserJpaEntity();
		userJpaEntity.updateProfileImgUrl(command.getProfileImgUrl());
		userJpaEntity.updateUserNickname(command.getNickname());
		userJpaEntity.updateUserContent(command.getContent());

		// TODO : 다이빙 풀 정보, 컨셉 정보 버디서비스 전달
	}

}
