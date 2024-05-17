package com.freediving.buddyservice.domain.query.component;

import java.time.LocalDateTime;
import java.util.Set;

import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.UserInfo;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.query.component.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.DivingPoolInfoResponse;
import com.freediving.buddyservice.domain.query.component.common.ParticipantInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BuddyEventlistingCardResponse implements QueryComponent {
	private UserInfo userInfo;
	private boolean isLiked;
	private Long likedCount;
	private Long eventId;
	private Set<DivingPoolInfoResponse> divingPools;
	private String comment;
	private Set<ConceptInfoResponse> concepts;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	private Long freedivingLevel;
	private BuddyEventStatus status;
	private Long participantCount;
	private Long currentParticipantCount;
	private Set<ParticipantInfoResponse> participantInfos;

}
