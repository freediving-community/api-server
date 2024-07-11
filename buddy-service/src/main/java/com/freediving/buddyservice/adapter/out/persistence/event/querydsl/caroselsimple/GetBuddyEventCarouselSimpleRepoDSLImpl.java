package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.caroselsimple;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
import com.freediving.common.enumerate.DivingPool;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GetBuddyEventCarouselSimpleRepoDSLImpl implements GetBuddyEventCarouselSimpleRepoDSL {

	private final EntityManager entityManager;

	@Override
	public List<GetBuddyEventCarouselSimpleQueryProjectionDto> getBuddyEventCarouselSimple(LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, DivingPool divingPool, Long excludedEventId) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT events.event_id AS eventId, ");
		sql.append("events.event_start_date AS eventStartDate, ");
		sql.append("events.event_end_date AS eventEndDate, ");
		sql.append("events.freediving_level AS freedivingLevel, ");
		sql.append("events.status AS status, ");
		sql.append("events.participant_count AS participantCount, ");
		sql.append("COUNT(DISTINCT requests.user_id) AS participantCountDistinct, ");
		sql.append("events.gender_type AS genderType, "); // 마지막 쉼표 제거
		sql.append("events.user_id AS userId ");
		sql.append("FROM buddy_event AS events ");
		sql.append("LEFT JOIN buddy_event_diving_pool_mapping AS pool ON events.event_id = pool.event_id ");
		sql.append("LEFT JOIN buddy_event_join_requests AS requests ON events.event_id = requests.event_id ");
		sql.append("AND requests.status IN ('" + ParticipationStatus.OWNER.name() + "','"
			+ ParticipationStatus.PARTICIPATING.name() + "') ");
		sql.append("WHERE events.event_start_date BETWEEN :startDate AND :endDate ");
		sql.append("AND events.status = 'RECRUITING' ");

		if (divingPool != null) {
			sql.append("AND pool.diving_pool_id = :divingPools ");
		}

		if (excludedEventId != null) {
			sql.append("AND events.event_id != :excludedEventId ");
		}

		sql.append(
			"GROUP BY events.event_id, events.event_start_date, events.event_end_date, events.freediving_level, events.status, events.participant_count ");
		sql.append("ORDER BY events.event_start_date ASC ");
		sql.append("LIMIT 10 OFFSET 0");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startDate", eventStartDate);
		query.setParameter("endDate", eventEndDate);

		if (divingPool != null) {
			query.setParameter("divingPools", divingPool.name());
		}

		if (excludedEventId != null) {
			query.setParameter("excludedEventId", excludedEventId);
		}

		List<Object[]> resultList = query.getResultList();

		List<GetBuddyEventCarouselSimpleQueryProjectionDto> events = resultList.stream()
			.map(product -> new GetBuddyEventCarouselSimpleQueryProjectionDto(
				product[0] != null ? ((Number)product[0]).longValue() : null, // eventId
				convertToLocalDateTime((Timestamp)product[1]), // eventStartDate
				convertToLocalDateTime((Timestamp)product[2]), // eventEndDate
				product[3] != null ? ((Number)product[3]).longValue() : null, // freedivingLevel
				product[4] != null ? BuddyEventStatus.valueOf((String)product[4]) : BuddyEventStatus.RECRUITMENT_CLOSED,
				product[5] != null ? ((Number)product[5]).longValue() : 0, // participantCount
				product[6] != null ? ((Number)product[6]).longValue() : 0,// currentParticipantCount
				product[7] != null ? GenderType.valueOf((String)product[7]) : GenderType.ALL,
				product[8] != null ? ((Number)product[8]).longValue() : -1))// userId
			.collect(Collectors.toList());

		return events;

	}

	private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}
}
