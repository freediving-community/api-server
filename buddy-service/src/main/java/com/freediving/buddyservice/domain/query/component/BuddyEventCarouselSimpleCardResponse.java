package com.freediving.buddyservice.domain.query.component;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.query.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.common.DivingPoolInfoResponse;
import com.freediving.buddyservice.domain.query.common.UserInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuddyEventCarouselSimpleCardResponse implements QueryComponent {

	private UserInfoResponse userInfo;
	private Long eventId;
	private Set<DivingPoolInfoResponse> divingPools;
	private Set<ConceptInfoResponse> concepts;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private Integer freedivingLevel;
	private BuddyEventStatus status;
	private Integer participantCount;
	private Integer currentParticipantCount;

}
