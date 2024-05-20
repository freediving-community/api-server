package com.freediving.memberservice.adapter.out.persistence;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.domain.member.RoleLevel;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.adapter.in.web.dto.CreateUserResponse;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommandV2;
import com.freediving.memberservice.application.port.in.CreateUserProfileCommand;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.application.port.out.CreateUserPortV2;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.exception.ErrorCode;
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
public class CreateUserPersistenceAdapter implements CreateUserPort, CreateUserPortV2 {
	private final UserJpaRepository userJpaRepository;
	private final UserLicenseJpaRepository userLicenseJpaRepository;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/17
	 * @Param            : CreateUserCommand
	 * @Return           : User 도메인
	 * @Description      : 최초 로그인인 경우 회원가입 / 기존 가입자인 경우 사용자 정보 반환
	 *                     소셜 로그인 시 이메일 2차 이메일 정보 이슈로 PK 값 변경 (providerId)
	 */
	@Override
	public CreateUserResponse createOrGetUser(CreateUserCommand createUserCommand) {
		final OauthType oauthType = createUserCommand.getOauthType();
		final String email = createUserCommand.getEmail();
		final String providerId = createUserCommand.getProviderId();
		final String profileImgUrl = createUserCommand.getProfileImgUrl();

		UserJpaEntity userJpaEntity = userJpaRepository.findByOauthTypeAndProviderId(oauthType, providerId)
			.orElse(null);

		// 최초 로그인인 경우
		if (ObjectUtils.isEmpty(userJpaEntity)) {
			UserJpaEntity createUserJpaEntity = UserJpaEntity.createSimpleUser(oauthType, email, profileImgUrl,
				providerId);
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
		List<UserLicenseJpaEntity> userLicenceJpaList = userLicenseJpaRepository.findAllByUserJpaEntity(userJpaEntity);
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

	@Override
	public void createUserProfile(CreateUserProfileCommand command) {
		UserJpaEntity userJpaEntity = userJpaRepository.findById(command.getUserId()).orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, ErrorCode.NOT_FOUND_USER.getMessage()));
		userJpaEntity.updateProfileImgUrl(command.getProfileImgUrl());
		userJpaEntity.updateUserNickname(command.getNickname());
		userJpaEntity.updateUserContent(command.getContent());
	}

	@Override
	public void createUserInfoV2(CreateUserInfoCommandV2 command) {
		final Long userId = command.getUserId();
		final DiveType diveType = command.getDiveType();
		final Integer licenseLevel = command.getLicenseLevel();
		final String licenseImgUrl = command.getLicenseImgUrl();

		if (!ObjectUtils.isEmpty(diveType)) {
			UserLicenseJpaEntity userLicenseJpaEntity = userLicenseJpaRepository.findByUserIdAndDiveType(userId,
				diveType);

			if (!ObjectUtils.isEmpty(licenseLevel)) {
				userLicenseJpaEntity.updateLicenseLevel(licenseLevel);
				// 0레벨인 경우 심사 없이 바로 승인 나머지는 심사중 상태로 변경
				if (!ObjectUtils.isEmpty(licenseImgUrl)) {
					userLicenseJpaEntity.updateLicenseImgUrl(licenseImgUrl);
					if (licenseLevel == 0) {
						userLicenseJpaEntity.updateRoleLevel(RoleLevel.NO_LEVEL);
					} else {
						userLicenseJpaEntity.updateRoleLevel(RoleLevel.WAIT_LICENSE_APPROVAL);
					}
				}
			}
		}
	}
}
