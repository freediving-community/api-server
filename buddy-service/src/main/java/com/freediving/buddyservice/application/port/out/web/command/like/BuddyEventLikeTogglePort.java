package com.freediving.buddyservice.application.port.out.web.command.like;

public interface BuddyEventLikeTogglePort {
	void buddyEventLikeToggle(final Long userId, final Long eventId, final boolean likeStatus);
}
