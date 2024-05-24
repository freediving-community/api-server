package com.freediving.buddyservice.adapter.out.persistence.preference;

import java.io.Serializable;
import java.util.Objects;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBuddyEventConceptId implements Serializable {
	private Long userId;
	private BuddyEventConcept conceptId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserBuddyEventConceptId that = (UserBuddyEventConceptId)o;
		return Objects.equals(userId, that.userId) &&
			conceptId == that.conceptId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, conceptId);
	}
}