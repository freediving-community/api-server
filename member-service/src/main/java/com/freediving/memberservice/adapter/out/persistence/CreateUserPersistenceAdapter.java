package com.freediving.memberservice.adapter.out.persistence;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.out.CreateUserLicencePort;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.RoleLevel;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.exception.ErrorCode;

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
public class CreateUserPersistenceAdapter implements CreateUserPort, CreateUserLicencePort {
	private final UserJpaRepository userJpaRepository;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/17
	 * @Param            : CreateUserCommand
	 * @Return           : User 도메인
	 * @Description      : 최초 로그인인 경우 회원가입 / 기존 가입자인 경우 사용자 정보 반환
	 */
	@Override
	public User createOrGetUser(CreateUserCommand createUserCommand) {
		final OauthType oauthType = createUserCommand.getOauthType();
		final String email = createUserCommand.getEmail();
		final String profileImgUrl = createUserCommand.getProfileImgUrl();

		UserJpaEntity userJpaEntity = userJpaRepository.findByOauthTypeAndEmail(oauthType, email).orElse(null);

		// 최초 로그인인 경우
		if (ObjectUtils.isEmpty(userJpaEntity)) {
			UserJpaEntity saveUserJpaEntity = UserJpaEntity
				.createSimpleUser(oauthType, email, profileImgUrl, RoleLevel.UNREGISTER);
			userJpaRepository.save(saveUserJpaEntity);
			return User.fromJpaEntitySimple(saveUserJpaEntity);
		}
		return User.fromJpaEntityDetail(userJpaEntity);
	}

	@Override
	public void createUserLicenceLevel(Long userId, Integer licenceLevel) {
		UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, ErrorCode.NOT_FOUND_USER.getMessage()));
		UserLicenceJpaEntity userLicenceJpaEntity = UserLicenceJpaEntity.createUserLicenceJpaEntity(licenceLevel);
		userJpaEntity.updateUserLicenceJpaEntity(userLicenceJpaEntity);
	}

	@Override
	public void createUserLicenceImgUrl(Long userId, String licenceImgUrl) {
		UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, ErrorCode.NOT_FOUND_USER.getMessage()));
		UserLicenceJpaEntity userLicenceJpaEntity = userJpaEntity.getUserLicenceJpaEntity();
		if (ObjectUtils.isEmpty(userLicenceJpaEntity)) {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, ErrorCode.INVALID_LICENCE_LEVEL.getMessage());
		}
		userLicenceJpaEntity.updateLicenceImgUrl(licenceImgUrl);
	}
}
