package com.freediving.memberservice.adapter.out.persistence;

import org.apache.commons.lang3.ObjectUtils;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;
import com.freediving.memberservice.application.port.in.CreateUserCommand;
import com.freediving.memberservice.application.port.in.CreateUserInfoCommand;
import com.freediving.memberservice.application.port.out.CreateUserPort;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.RoleLevel;
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
public class CreateUserPersistenceAdapter implements CreateUserPort {
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
			UserJpaEntity createUserJpaEntity = UserJpaEntity
				.createSimpleUser(oauthType, email, profileImgUrl, RoleLevel.UNREGISTER);
			UserJpaEntity savedUserJpaEntity = userJpaRepository.save(createUserJpaEntity);
			String randomNickname = NicknameGenerator.generateNickname(savedUserJpaEntity.getUserId());
			savedUserJpaEntity.updateUserNickname(randomNickname);
			return User.fromJpaEntitySimple(savedUserJpaEntity);
		}
		return User.fromJpaEntityDetail(userJpaEntity);
	}

	@Override
	public void createUserInfo(CreateUserInfoCommand command) {
		Long userId = command.getUserId();
		UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow(
			() -> new BuddyMeException(ServiceStatusCode.BAD_REQUEST, ErrorCode.NOT_FOUND_USER.getMessage()));

		UserLicenceJpaEntity userLicenceJpaEntity = createUserLicence(command);
		userJpaEntity.updateUserLicenceJpaEntity(userLicenceJpaEntity);
		userJpaEntity.updateUserNickname(command.getNickname());
		userJpaEntity.updateUserContent(command.getContent());

		// TODO : 다이빙 풀 정보, 컨셉 정보 버디서비스 전달
	}

	private UserLicenceJpaEntity createUserLicence(CreateUserInfoCommand command) {
		return UserLicenceJpaEntity.createUserLicenceJpaEntity(command.getDiveType(), command.getLicenceLevel(),
			command.getLicenceImgUrl());
	}

}
