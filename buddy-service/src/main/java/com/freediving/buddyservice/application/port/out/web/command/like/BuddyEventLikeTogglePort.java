package com.freediving.buddyservice.application.port.out.web.command.like;

import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.BuddyEventLikeMappingJpaEntity;

public interface BuddyEventLikeTogglePort {

	/**
	 * 사용자가 특정 이벤트에 대해서 좋아요(관심)을 하고있는지 체크한다.
	 *
	 * @param buddyEventJpaEntity eventId가 채워진 버디 이벤트 Jpa객체
	 * @param userId 사용자 식별 Id
	 * @return 존재하면 Jpa객체 리턴, 없으면 null
	 */
	BuddyEventLikeMappingJpaEntity existBuddyEventLikeMapping(final BuddyEventJpaEntity buddyEventJpaEntity,
		final Long userId);

	Integer buddyEventLikeToggleOn(final BuddyEventJpaEntity buddyEventJpaEntity,
		final Long userId, final BuddyEventLikeMappingJpaEntity existBuddyEventLikeMapping);

	Integer buddyEventLikeToggleOff(final BuddyEventJpaEntity buddyEventJpaEntity,
		final Long userId, final BuddyEventLikeMappingJpaEntity existBuddyEventLikeMapping);

	void buddyEventLikeToggleSet(final BuddyEventJpaEntity buddyEventJpaEntity);

}
