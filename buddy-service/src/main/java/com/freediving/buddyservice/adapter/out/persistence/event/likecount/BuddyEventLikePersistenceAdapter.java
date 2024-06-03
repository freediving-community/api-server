package com.freediving.buddyservice.adapter.out.persistence.event.likecount;

import java.util.Optional;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.application.port.out.web.command.like.BuddyEventLikeTogglePort;
import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class BuddyEventLikePersistenceAdapter implements BuddyEventLikeTogglePort {

	private final BuddyEventLikeCountRepository buddyEventLikeCountRepository;
	private final BuddyEventLikeMappingRepository buddyEventLikeMappingRepository;

	@Override
	public BuddyEventLikeMappingJpaEntity existBuddyEventLikeMapping(BuddyEventJpaEntity buddyEventJpaEntity,
		Long userId) {

		BuddyEventLikeMappingJpaEntity entity = buddyEventLikeMappingRepository.findByBuddyEventAndUserId(
			buddyEventJpaEntity, userId);

		return entity;
	}

	/**
	 * 버디 이벤트 좋아요 카운트를 1 증가 시킨다.
	 * 버디 이벤트 좋아요 이력을 쌓는다.
	 * @param buddyEventJpaEntity 대상 버디 이벤트
	 * @param  userId 좋아요를 한 사용자 식별 ID
	 */
	@Override
	public Integer buddyEventLikeToggleOn(final BuddyEventJpaEntity buddyEventJpaEntity,
		final Long userId, final BuddyEventLikeMappingJpaEntity existBuddyEventLikeMapping) {

		Optional<BuddyEventLikeCountJpaEntity> buddyEventLikeCountJpaEntity = buddyEventLikeCountRepository.findById(
			buddyEventJpaEntity.getEventId());

		if (buddyEventLikeCountJpaEntity.isPresent() == false)
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "BuddyEvent like Mapping is null");

		buddyEventLikeCountJpaEntity.get().likeCountUp();

		// 좋아요 매핑이 존재하지 않으면  Insert
		if (existBuddyEventLikeMapping == null)
			buddyEventLikeMappingRepository.save(
				BuddyEventLikeMappingJpaEntity.builder().buddyEvent(buddyEventJpaEntity)
					.userId(userId).isDeleted(false).build());
		else { // 좋아요 매핑이 존재하면 토글 On ( delete값 변경 )
			existBuddyEventLikeMapping.likeToggleOn();
			buddyEventLikeMappingRepository.save(existBuddyEventLikeMapping);
		}

		buddyEventLikeCountRepository.save(buddyEventLikeCountJpaEntity.get());
		return buddyEventLikeCountJpaEntity.get().getLikeCount();
	}

	@Override
	public Integer buddyEventLikeToggleOff(final BuddyEventJpaEntity buddyEventJpaEntity,
		final Long userId, final BuddyEventLikeMappingJpaEntity existBuddyEventLikeMapping) {

		Optional<BuddyEventLikeCountJpaEntity> buddyEventLikeCountJpaEntity = buddyEventLikeCountRepository.findById(
			buddyEventJpaEntity.getEventId());

		if (buddyEventLikeCountJpaEntity.isPresent() == false)
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "BuddyEvent like Mapping is null");

		//todo 동시성 문제 해결 필요함.
		buddyEventLikeCountJpaEntity.get().likeCountDown();

		// 좋아요 매핑이 존재 하면
		if (existBuddyEventLikeMapping != null) {
			existBuddyEventLikeMapping.likeToggleOff();
			buddyEventLikeMappingRepository.save(existBuddyEventLikeMapping);
		}

		buddyEventLikeCountRepository.save(buddyEventLikeCountJpaEntity.get());
		return buddyEventLikeCountJpaEntity.get().getLikeCount();
	}

	@Override
	public void buddyEventLikeToggleSet(BuddyEventJpaEntity buddyEventJpaEntity) {
		buddyEventLikeCountRepository.save(
			BuddyEventLikeCountJpaEntity.builder().buddyEvent(buddyEventJpaEntity).likeCount(0).build());
	}

}
