package com.freediving.buddyservice.application.port.out.web.query;

import java.util.List;

import com.freediving.buddyservice.adapter.out.persistence.concept.BuddyEventConceptJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.preference.UserBuddyEventConceptEntity;

public interface GetBuddyEventConceptListPort {

	List<BuddyEventConceptJpaEntity> getEventConceptList();

	List<UserBuddyEventConceptEntity> getEventConceptListForInternal(Long userId);
}
