package com.freediving.buddyservice.adapter.out.persistence.event.likecount;

import com.freediving.buddyservice.application.port.out.web.command.like.BuddyEventLikeTogglePort;
import com.freediving.common.config.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class BuddyEventLikeCountPersistenceAdapter implements BuddyEventLikeTogglePort {

	private final BuddyEventLikeCountRepository buddyEventLikeCountRepository;

	@Override
	public void buddyEventLikeToggle(final Long userId, final Long eventId, final boolean likeStatus) {
		
	}
}
