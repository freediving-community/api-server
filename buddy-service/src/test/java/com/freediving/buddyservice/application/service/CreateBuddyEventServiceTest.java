package com.freediving.buddyservice.application.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.buddyservice.adapter.out.externalservice.FindUser;
import com.freediving.buddyservice.adapter.out.externalservice.LicenseInfo;
import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.BuddyEventRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.BuddyEventConceptMappingRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.BuddyEventDivingPoolMappingRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.join.BuddyEventJoinRequestRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.BuddyEventLikeCountRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.likecount.BuddyEventLikeMappingRepository;
import com.freediving.buddyservice.adapter.out.persistence.event.viewcount.BuddyEventViewCountRepository;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.web.command.CreateBuddyEventUseCase;
import com.freediving.buddyservice.application.port.out.externalservice.query.RequestMemberPort;
import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.command.CreatedBuddyEventResponse;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.common.domain.member.FreeDiving;
import com.freediving.common.domain.member.RoleLevel;
import com.freediving.common.enumerate.DivingPool;

@SpringBootTest
@ActiveProfiles("local")
class CreateBuddyEventServiceTest {

	@Autowired
	BuddyEventConceptMappingRepository buddyEventConceptMappingRepository;
	@Autowired
	BuddyEventDivingPoolMappingRepository buddyEventDivingPoolMappingRepository;
	@Autowired
	BuddyEventJoinRequestRepository buddyEventJoinRequestRepository;
	@Autowired
	BuddyEventLikeCountRepository buddyEventLikeCountRepository;
	@Autowired
	BuddyEventViewCountRepository buddyEventViewCountRepository;
	@Autowired
	private CreateBuddyEventUseCase createBuddyEventUseCase;
	@Autowired
	private BuddyEventRepository buddyEventRepository;
	@MockBean
	private RequestMemberPort requestMemberPort;
	@Autowired
	BuddyEventLikeMappingRepository buddyEventLikeMappingRepository;

	@AfterEach
	void tearDown() {
		buddyEventConceptMappingRepository.deleteAllInBatch();
		buddyEventDivingPoolMappingRepository.deleteAllInBatch();
		buddyEventJoinRequestRepository.deleteAllInBatch();
		buddyEventLikeCountRepository.deleteAllInBatch();
		buddyEventLikeMappingRepository.deleteAllInBatch();
		buddyEventViewCountRepository.deleteAllInBatch();
		buddyEventRepository.deleteAllInBatch();

	}

	@DisplayName("비정상적인 사용자의 버디 이벤트 생성 요청인 경우 실패한다.")
	@Test
	void shouldRejectEventCreationForInvalidUser() {

		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 1. CreateBuddyEventUseCase를 발생 할 수 있는 Command 생성.
		CreateBuddyEventCommand command = CreateBuddyEventCommand.builder()
			.userId(userId)
			.eventStartDate(LocalDateTime.now().plusHours(1))
			.eventEndDate(LocalDateTime.now().plusHours(2))
			.participantCount(3)
			.genderType(GenderType.ALL)
			.divingPools(Set.of(DivingPool.PARADIVE))
			.freedivingLevel(2)
			.carShareYn(true)
			.comment("zzzz")
			.build();

		// 2. RequestMemberPort의 MemberStauts 상태 조회 결과를 실패로 만든다.
		HashMap<Long, FindUser> dump = new HashMap<>();
		dump.put(userId,
			FindUser.builder().userId(userId).nickname("임시 사용자-" + userId).licenseInfo(LicenseInfo.builder()
				.freeDiving(
					new FreeDiving(RoleLevel.UNREGISTER.getLevel(), RoleLevel.UNREGISTER.name(), null, "",
						false))
				.build()).build());
		Mockito.when(requestMemberPort.getMemberStatus(Mockito.any(List.class)))
			.thenReturn(dump);

		// when, then
		// assertThatThrownBy(() -> createBuddyEventUseCase.createBuddyEvent(command))
		// 	.isInstanceOf(RuntimeException.class)
		// 	.hasMessage("비정상적인 사용자.");
	}

	@DisplayName("이미 모집 중인 버디 이벤트와 겹치는 시간의 이벤트를 생성하려고 하면 실패한다.")
	@Test
	void overlappingBuddyEventCreationShouldBeRejected() {
		//given
		Random random = new Random();
		Long userId = random.nextLong();

		// 10시 ~ 18시
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusHours(4);

		BuddyEventJpaEntity buddyEventJpaEntity = generateBuddyEventJpa(userId, startDate, endDate, 3, null);
		buddyEventRepository.save(buddyEventJpaEntity);

		LocalDateTime createStartDate = LocalDateTime.now().plusHours(3);
		LocalDateTime createEndDate = LocalDateTime.now().plusHours(7);

		// 1. CreateBuddyEventUseCase를 발생 할 수 있는 Command 생성.
		CreateBuddyEventCommand command = CreateBuddyEventCommand.builder()
			.userId(userId)
			.eventStartDate(createStartDate)
			.eventEndDate(createEndDate)
			.participantCount(3)
			.genderType(GenderType.ALL)
			.carShareYn(true)
			.comment("zzzz")
			.build();

		// 2. RequestMemberPort의 MemberStauts 상태 조회 결과를 성공으로 만든다.
		HashMap<Long, FindUser> dump = new HashMap<>();
		dump.put(userId,
			FindUser.builder().userId(userId).nickname("임시 사용자-" + userId).licenseInfo(LicenseInfo.builder()
				.freeDiving(
					new FreeDiving(RoleLevel.UNREGISTER.getLevel(), RoleLevel.UNREGISTER.name(), null, "",
						false))
				.build()).build());
		Mockito.when(requestMemberPort.getMemberStatus(Mockito.any(List.class)))
			.thenReturn(dump);

		//when, then
		assertThatThrownBy(() -> createBuddyEventUseCase.createBuddyEvent(command))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("버디 일정이 겹칩니다.");

	}

	@DisplayName("정상적인 사용자와 이미 생성된 이벤트가 없거나 시간이 겹치지 않는다면 이벤트 생성 성공한다.")
	@Test
	void shouldCreateEventSuccessWithUserAndNoOverlapEventDate() {
		// given
		Random random = new Random();
		Long userId = random.nextLong();

		// 10시 ~ 18시
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusHours(4);

		BuddyEventJpaEntity buddyEventJpaEntity = generateBuddyEventJpa(userId, startDate, endDate, 3, null);
		buddyEventRepository.save(buddyEventJpaEntity);

		LocalDateTime createStartDate = LocalDateTime.now().plusHours(5);
		LocalDateTime createEndDate = LocalDateTime.now().plusHours(7);

		//  CreateBuddyEventUseCase를 발생 할 수 있는 Command 생성.
		CreateBuddyEventCommand command = CreateBuddyEventCommand.builder()
			.userId(userId)
			.eventStartDate(createStartDate)
			.eventEndDate(createEndDate)
			.participantCount(3)
			.genderType(GenderType.ALL)
			.carShareYn(true)
			.comment("zzzz")
			.divingPools(Set.of(DivingPool.PARADIVE))
			.freedivingLevel(2)
			.build();

		//  RequestMemberPort의 MemberStauts 상태 조회 결과를 성공으로 만든다.
		HashMap<Long, FindUser> dump = new HashMap<>();
		dump.put(userId,
			FindUser.builder().userId(userId).nickname("임시 사용자-" + userId).licenseInfo(LicenseInfo.builder()
				.freeDiving(
					new FreeDiving(RoleLevel.UNREGISTER.getLevel(), RoleLevel.UNREGISTER.name(), null, "",
						false))
				.build()).build());
		Mockito.when(requestMemberPort.getMemberStatus(Mockito.any(List.class)))
			.thenReturn(dump);

		// when
		CreatedBuddyEventResponse createdBuddyEventResponse = createBuddyEventUseCase.createBuddyEvent(command);

		// then
		assertThat(createdBuddyEventResponse).extracting("eventId", "userId", "eventStartDate", "eventEndDate",
				"participantCount", "freedivingLevel",
				"status", "carShareYn", "comment")
			.contains(createdBuddyEventResponse.getEventId(), userId, createStartDate,
				createEndDate, 3,
				2,
				BuddyEventStatus.RECRUITING, true, "zzzz");

	}

	private BuddyEventJpaEntity generateBuddyEventJpa(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Integer participantCount, String comment) {

		List<BuddyEventConcept> buddyEventConcepts = List.of(BuddyEventConcept.FUN, BuddyEventConcept.PRACTICE);

		return BuddyEventJpaEntity.builder()
			.userId(userId)
			.eventStartDate(eventStartDate)
			.eventEndDate(eventEndDate)
			.participantCount(participantCount)
			.freedivingLevel(0)
			.status(BuddyEventStatus.RECRUITING)
			.genderType(GenderType.ALL)
			.carShareYn(Boolean.FALSE)
			.comment(comment)
			.build();

	}
}