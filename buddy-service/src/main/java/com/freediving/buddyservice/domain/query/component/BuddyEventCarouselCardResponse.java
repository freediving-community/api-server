package com.freediving.buddyservice.domain.query.component;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.query.component.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.DivingPoolInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.UserInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuddyEventCarouselCardResponse implements QueryComponent {

	private UserInfoResponse user;
	private boolean isLiked;
	private Long eventId;
	private Set<DivingPoolInfoResponse> divingPools;
	private String comment;
	private Set<ConceptInfoResponse> concepts;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private Integer freedivingLevel;
	private BuddyEventStatus status;
	private Integer participantCount;
	private Integer currentParticipantCount;

}
