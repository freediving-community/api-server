package com.freediving.buddyservice.application.service.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.buddyservice.adapter.out.externalservice.member.userinfo.dto.UserInfo;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.querydsl.BuddyEventConceptMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.querydsl.BuddyEventDivingPoolMappingProjectDto;
import com.freediving.buddyservice.adapter.out.persistence.event.querydsl.BuddyEventDetailQueryProjectionDto;
import com.freediving.buddyservice.application.port.in.web.query.BuddyEventDetailCommand;
import com.freediving.buddyservice.application.port.in.web.query.BuddyEventDetailUseCase;
import com.freediving.buddyservice.application.port.out.Internalservice.query.RequestMemberPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventConceptMappingPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventDetailPort;
import com.freediving.buddyservice.application.port.out.web.query.BuddyEventDivingPoolMappingPort;
import com.freediving.buddyservice.domain.query.QueryBuddyEventDetailResponse;
import com.freediving.buddyservice.domain.query.common.ConceptInfoResponse;
import com.freediving.buddyservice.domain.query.common.DivingPoolInfoResponse;
import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class BuddyEventDetailService implements BuddyEventDetailUseCase {

	private final BuddyEventDetailPort buddyEventDetailPort;
	private final RequestMemberPort requestMemberPort;
	private final BuddyEventConceptMappingPort buddyEventConceptMappingPort;
	private final BuddyEventDivingPoolMappingPort buddyEventDivingPoolMappingPort;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public QueryBuddyEventDetailResponse getBuddyEventDetail(BuddyEventDetailCommand command) {

		BuddyEventDetailQueryProjectionDto buddyEventDetail = buddyEventDetailPort.getBuddyEventDetail(
			command.getUserId(), command.getEventId());

		if (buddyEventDetail == null)
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		final List<Long> ids = new ArrayList<>();
		ids.add(buddyEventDetail.getEventId());

		Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> allDivingPoolMappingByEventId = buddyEventDivingPoolMappingPort.getAllDivingPoolMapping(
			ids);

		Map<Long, List<BuddyEventConceptMappingProjectDto>> allConceptMappingByEventId = buddyEventConceptMappingPort.getAllConceptMapping(
			ids);

		Set<Long> userIds = new HashSet<>();
		userIds.add(buddyEventDetail.getUserId());

		HashMap<Long, UserInfo> userHashMap = requestMemberPort.getMemberStatus(userIds.stream().toList());

		//작성자 정보를 못가져오면 제거.
		UserInfo buddyOwner = userHashMap.get(buddyEventDetail.getUserId());
		if (buddyOwner == null)
			throw new BuddyMeException(ServiceStatusCode.NO_CONTENT);

		List<BuddyEventConceptMappingProjectDto> conceptMappings = allConceptMappingByEventId.get(
			buddyEventDetail.getEventId());
		List<BuddyEventDivingPoolMappingProjectDto> divingPoolMappings = allDivingPoolMappingByEventId.get(
			buddyEventDetail.getEventId());

		QueryBuddyEventDetailResponse response = QueryBuddyEventDetailResponse.builder()
			.userInfo(buddyOwner.toResponse())
			.isLiked(buddyEventDetail.isLiked())
			.likedCount(buddyEventDetail.getLikedCount())
			.eventId(buddyEventDetail.getEventId())
			.divingPools(Optional.ofNullable(divingPoolMappings)
				.orElse(Collections.emptyList()).stream()
				.map(e -> DivingPoolInfoResponse.builder()
					.divingPoolId(e.getDivingPoolId())
					.divingPoolName(e.getDivingPoolName())
					.build())
				.collect(
					Collectors.toSet()))
			.comment(buddyEventDetail.getComment())
			.concepts(Optional.ofNullable(conceptMappings)
				.orElse(Collections.emptyList()).stream()
				.map(e -> ConceptInfoResponse.builder()
					.conceptId(e.getConceptId())
					.conceptName(e.getConceptName())
					.build())
				.collect(
					Collectors.toSet()))
			.eventStartDate(buddyEventDetail.getEventStartDate())
			.eventEndDate(buddyEventDetail.getEventEndDate())
			.freedivingLevel(buddyEventDetail.getFreedivingLevel())
			.status(buddyEventDetail.getStatus())
			.participantCount(buddyEventDetail.getParticipantCount())
			.currentParticipantCount(buddyEventDetail.getCurrentParticipantCount() - 1)
			.genderType(buddyEventDetail.getGenderType())
			.carShareYn(buddyEventDetail.isCarShareYn())
			.imageUrl(buddyEventDetail.getImageUrl())
			.viewCount(buddyEventDetail.getViewCount())
			.build();

		return response;
	}
}
