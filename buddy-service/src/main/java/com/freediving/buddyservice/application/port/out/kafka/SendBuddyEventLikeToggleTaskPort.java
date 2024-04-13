package com.freediving.buddyservice.application.port.out.kafka;

import com.freediving.buddyservice.common.task.domain.BuddyEventLikeToggleTask;

public interface SendBuddyEventLikeToggleTaskPort {

	void sendRechargingMoneyTaskPort(
		BuddyEventLikeToggleTask task
	);
}
