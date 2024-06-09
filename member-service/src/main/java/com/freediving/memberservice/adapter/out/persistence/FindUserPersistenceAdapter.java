package com.freediving.memberservice.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.memberservice.adapter.out.dto.UserConceptResponse;
import com.freediving.memberservice.adapter.out.dto.UserPoolResponse;
import com.freediving.memberservice.adapter.out.service.BuddyExternalAdapter;
import com.freediving.memberservice.application.port.out.FindUserPort;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserStatus;
import com.freediving.memberservice.exception.ErrorCode;
import com.freediving.memberservice.exception.MemberServiceException;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class FindUserPersistenceAdapter implements FindUserPort {

	private static final Logger log = LoggerFactory.getLogger(FindUserPersistenceAdapter.class);
	private final UserJpaRepository userJpaRepository;
	private final UserLicenseJpaRepository userLicenseJpaRepository;
	private final BuddyExternalAdapter buddyExternalAdapter;

	@Override
	public User findUserDetailById(Long userId) {
		UserJpaEntity userJpaEntity = userJpaRepository.findUserDetailById(userId)
			.orElseThrow(() -> new MemberServiceException(ErrorCode.NOT_FOUND_USER));
		List<UserLicenseJpaEntity> userLicenseJpaEntityList = userLicenseJpaRepository.findAllByUserJpaEntity(
			userJpaEntity);
		List<String> conceptList = null;
		List<String> poolList = null;

		try {
			ResponseEntity<ResponseJsonObject<UserPoolResponse>> userPoolList = buddyExternalAdapter.userDivingPoolListByUserId(userId);
			ResponseEntity<ResponseJsonObject<UserConceptResponse>> userConceptList = buddyExternalAdapter.userConceptListByUserId(userId);
			poolList = userPoolList.getBody().getData().getDivingPools();
			conceptList = userConceptList.getBody().getData().getConcepts();
			log.info("userDivingPoolList" + poolList);
			log.info("userConceptList" + conceptList);
		} catch (Exception e) {
			log.error("BuddyService Exception - findUserDetailById : {}", userId, e);
		}

		return User.fromJpaEntityListAndInternalInfo(userJpaEntity, userLicenseJpaEntityList, conceptList, poolList);
	}

	@Override
	public List<User> findUserListByIds(List<Long> userIds) {
		List<UserJpaEntity> userJpaEntityList = userJpaRepository.findAllById(userIds);

		return userJpaEntityList.stream()
			.map(e -> {
				if (e.getUserStatus().equals(UserStatus.WITHDRAWN)) {
					e.updateUserNickname(UserStatus.WITHDRAWN.getCode());
				}
				return User.fromJpaEntityList(e, userLicenseJpaRepository.findAllByUserJpaEntity(e));
			})
			.collect(Collectors.toList());
	}

	@Override
	public boolean findNickname(String trimSafeNickname) {
		return userJpaRepository.findByNickname(trimSafeNickname).isPresent();
	}
}
