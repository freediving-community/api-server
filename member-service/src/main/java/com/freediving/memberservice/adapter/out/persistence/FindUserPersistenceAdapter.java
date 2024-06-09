package com.freediving.memberservice.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.memberservice.adapter.in.web.dto.FindMyPageResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserInfoResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserLicense;
import com.freediving.memberservice.adapter.in.web.dto.FindUserLicenseResponse;
import com.freediving.memberservice.adapter.in.web.dto.LicenseInfo;
import com.freediving.memberservice.adapter.out.dto.UserConceptResponse;
import com.freediving.memberservice.adapter.out.dto.UserKeyword;
import com.freediving.memberservice.adapter.out.dto.UserPoolResponse;
import com.freediving.memberservice.adapter.out.dto.UserReview;
import com.freediving.memberservice.adapter.out.dto.UserReviewResponse;
import com.freediving.memberservice.adapter.out.dto.UserStory;
import com.freediving.memberservice.adapter.out.dto.UserStoryResponse;
import com.freediving.memberservice.adapter.out.service.BuddyExternalAdapter;
import com.freediving.memberservice.application.port.out.FindUserPort;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserStatus;
import com.freediving.memberservice.exception.ErrorCode;
import com.freediving.memberservice.exception.MemberServiceException;

import lombok.Getter;
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

	@Override
	public User findUserDetailById(Long userId) {
		return getUserDetail(userId, true).getUser();
	}

	@Override
	public FindUserInfoResponse findUserInfoByQuery(Long userId) {
		UserDetailInfo userDetailInfo = getUserDetail(userId, true);
		User user = userDetailInfo.getUser();
		LicenseInfo licenseInfo = LicenseInfo.createLicenseInfo(user.userLicenseList());

		List<UserStory> storyList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			UserStory story = UserStory.builder()
				.title( i+1 + "번째 스토리 제목")
				.content("스토리 내용 입니다.")
				.build();
			storyList.add(story);
		}

		List<UserReview> reviewList = new ArrayList<>();
		UserReview review = UserReview.builder()
			.userId(userId)
			.profileImgUrl(user.profileImgUrl())
			.nickname(user.nickname())
			.licenseLevel(licenseInfo.getFreeDiving().getLicenseLevel())
			.buddyExprCnt(5)
			.content("사진을 잘찍어줘서 좋아요!")
			.writeYYYYMMDD("2024.06.29")
			.pool("PARADIVE")
			.build();
		reviewList.add(review);

		List<UserKeyword> keywordList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			UserKeyword keyword = UserKeyword.builder()
				.emoticon("😀")
				.title(i+1 + "번째 키워드")
				.cnt(5 - i)
				.build();
			keywordList.add(keyword);
		}
		UserReviewResponse reviewResponse = UserReviewResponse.builder()
			.reviewCnt(1)
			.keywordList(keywordList)
			.reviewList(reviewList)
			.build();


		UserStoryResponse storyResponse = UserStoryResponse.builder()
			.storyCnt(5)
			.storyList(storyList)
			.build();

		return FindUserInfoResponse.builder()
			.userId(user.userId())
			.profileImgUrl(user.profileImgUrl())
			.nickname(user.nickname())
			.content(user.content())
			.licenseInfo(licenseInfo)
			.poolList(userDetailInfo.pools)
			.conceptList(userDetailInfo.concepts)
			.story(storyResponse)
			.review(reviewResponse)
			.build();
	}

	@Override
	public FindMyPageResponse findMyPageByUserId(Long userId) {
		UserDetailInfo userDetailInfo = getUserDetail(userId, false);
		User user =userDetailInfo.user;
		return FindMyPageResponse.from(user, 10, 5);
	}

	private UserDetailInfo getUserDetail(Long userId, boolean preferInfoTF) {
		UserJpaEntity userJpaEntity = userJpaRepository.findUserDetailById(userId)
			.orElseThrow(() -> new MemberServiceException(ErrorCode.NOT_FOUND_USER));
		List<UserLicenseJpaEntity> userLicenseJpaEntityList = userLicenseJpaRepository.findAllByUserJpaEntity(userJpaEntity);

		if (preferInfoTF) {
			List<String> conceptList = null;
			List<String> poolList = null;

			try {
				ResponseEntity<ResponseJsonObject<UserPoolResponse>> userPoolList = buddyExternalAdapter.userDivingPoolListByUserId(userId);
				ResponseEntity<ResponseJsonObject<UserConceptResponse>> userConceptList = buddyExternalAdapter.userConceptListByUserId(userId);
				poolList = userPoolList.getBody().getData().getDivingPools();
				conceptList = userConceptList.getBody().getData().getConcepts();
				log.info("userDivingPoolList: {}", poolList);
				log.info("userConceptList: {}", conceptList);
			} catch (Exception e) {
				log.error("BuddyService Exception - findUserPreferenceInfoById: {}", userId, e);
			}

			User user = User.fromJpaEntityListAndInternalInfo(userJpaEntity, userLicenseJpaEntityList, conceptList, poolList);
			return new UserDetailInfo(user, conceptList, poolList);
		}

		else {
			User user = User.fromJpaEntityList(userJpaEntity, userLicenseJpaEntityList);
			return new UserDetailInfo(user);
		}
	}

	@Getter
	private static class UserDetailInfo {
		private final User user;
		private List<String> concepts;
		private List<String> pools;

		public UserDetailInfo(User user, List<String> concepts, List<String> pools) {
			this.user = user;
			this.concepts = concepts;
			this.pools = pools;
		}

		public UserDetailInfo(User user) {
			this.user = user;
		}
	}
}
