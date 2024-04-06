package com.freediving.buddyservice.domain.query.component;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.common.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.common.enumeration.BuddyEventStatus;
import com.freediving.common.enumerate.DivingPool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuddyEventCarouselCardResponse implements QueryComponent{

	private UserInfo user;
	private boolean isLiked;
	private Long eventId;
	private Set<DivingPoolInfo> divingPools;
	private String comment;
	private Set<ConceptInfo> concepts;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private Integer freedivingLevel;
	private BuddyEventStatus status;
	private Integer participantCount;
	private Integer currentParticipantCount;

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	private class UserInfo{
		private Long userId;
		private String profileUrl;
		private String Nickname;
		private Integer freedivingLevel;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	private class DivingPoolInfo{
		private DivingPool divingPoolId;
		private String divingPoolName;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	private class ConceptInfo{
		private BuddyEventConcept conceptId;
		private String conceptName;
	}


}
