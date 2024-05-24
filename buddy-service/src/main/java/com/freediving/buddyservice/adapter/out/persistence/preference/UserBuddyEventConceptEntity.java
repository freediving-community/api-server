package com.freediving.buddyservice.adapter.out.persistence.preference;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Entity
@Table(name = "user_buddy_event_concept")
@IdClass(UserBuddyEventConceptId.class)
public class UserBuddyEventConceptEntity {

	@Id
	private Long userId;

	@Id
	@Enumerated(EnumType.STRING)
	private BuddyEventConcept conceptId;
}