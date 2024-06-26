package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.carousel;

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
public class GetBuddyEventCarouselRepoDSLImpl implements GetBuddyEventCarouselRepoDSL {

	private final EntityManager entityManager;

	@Override
	public List<GetBuddyEventCarouselQueryProjectionDto> getBuddyEventCarousel(Long userId,
		LocalDateTime eventStartDate,
		int pageNumber,
		int offset) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT events.event_id AS eventId, ");
		sql.append("events.event_start_date AS eventStartDate, ");
		sql.append("events.event_end_date AS eventEndDate, ");
		sql.append("CASE WHEN likeMapping.event_id IS NOT NULL THEN TRUE ELSE FALSE END AS isLiked, ");
		sql.append("like_count.like_count AS likeCount, ");
		sql.append("SUBSTRING(events.comment,1,100) AS comment, ");
		sql.append("events.freediving_level AS freedivingLevel, ");
		sql.append("events.status AS status, ");
		sql.append("events.participant_count AS participantCount, ");
		sql.append("COUNT(DISTINCT requests.user_id) AS participantCountDistinct, ");
		sql.append("events.gender_type AS gender_type, ");
		sql.append("events.user_id AS userId ");
		sql.append("FROM buddy_event AS events ");
		sql.append("LEFT JOIN buddy_event_diving_pool_mapping AS pool ON events.event_id = pool.event_id ");
		sql.append("LEFT JOIN buddy_event_like_count AS like_count ON events.event_id = like_count.event_id ");
		sql.append(
			"LEFT JOIN buddy_event_like_mapping AS likeMapping ON events.event_id = likeMapping.event_id AND likeMapping.user_id = :userId AND likeMapping.is_deleted = false ");
		sql.append(
			"LEFT JOIN buddy_event_join_requests AS requests ON events.event_id = requests.event_id  AND requests.status in (");
		sql.append("'" + ParticipationStatus.OWNER.name() + "','" + ParticipationStatus.PARTICIPATING.name() + "') ");
		sql.append("WHERE events.event_start_date >= :startDate ");
		sql.append("AND events.status = 'RECRUITING' ");

		sql.append(
			"GROUP BY events.event_id, events.event_start_date, events.event_end_date, likeMapping.event_id, like_count.like_count, events.comment, events.freediving_level, events.status, events.participant_count ");

		sql.append("ORDER BY events.event_start_date ASC ");
		sql.append("LIMIT :limit OFFSET :offset");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("userId", userId);
		query.setParameter("startDate", eventStartDate);
		query.setParameter("limit", offset);
		query.setParameter("offset", (pageNumber - 1) * offset);

		List<Object[]> resultList = query.getResultList();

		List<GetBuddyEventCarouselQueryProjectionDto> events = resultList.stream()
			.map(product -> new GetBuddyEventCarouselQueryProjectionDto(
				product[0] != null ? ((Number)product[0]).longValue() : null, // eventId
				convertToLocalDateTime((Timestamp)product[1]), // eventStartDate
				convertToLocalDateTime((Timestamp)product[2]), // eventEndDate
				product[3] != null ? (Boolean)product[3] : false, // isLiked
				product[4] != null ? ((Number)product[4]).longValue() : 0, // likedCount
				product[5] != null ? (String)product[5] : "", // comment
				product[6] != null ? ((Number)product[6]).longValue() : null, // freedivingLevel
				product[7] != null ? BuddyEventStatus.valueOf((String)product[7]) : BuddyEventStatus.RECRUITMENT_CLOSED,
				// status
				product[8] != null ? ((Number)product[8]).longValue() : 0, // participantCount
				product[9] != null ? ((Number)product[9]).longValue() : 0,// currentParticipantCount
				product[10] != null ? GenderType.valueOf((String)product[10]) : GenderType.ALL,
				product[11] != null ? ((Number)product[11]).longValue() : -1))// userId
			.collect(Collectors.toList());

		return events;
	}

	private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}

	@Override
	public Long countOfGetBuddyEventCarousel(Long userId,
		LocalDateTime eventStartDate) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT events.event_id) ");
		sql.append("FROM buddy_event AS events ");
		sql.append("WHERE events.event_start_date >= :startDate  ");
		sql.append("AND events.status = 'RECRUITING' ");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startDate", eventStartDate);

		return ((Number)query.getSingleResult()).longValue();
	}

	@Override
	public Long countOfGetHomeActiveBuddyEvent(Long userId, LocalDateTime eventStartDate) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT events.event_id) ");
		sql.append("FROM buddy_event AS events ");
		sql.append("WHERE events.event_start_date >= :startDate  ");
		sql.append("AND events.status = 'RECRUITING' ");

		Query query = entityManager.createNativeQuery(sql.toString());

		query.setParameter("startDate", eventStartDate);

		return ((Number)query.getSingleResult()).longValue();
	}

	@Override
	public List<GetBuddyEventCarouselQueryProjectionDto> getHomePreferencePoolBuddyEvent(Long userId,
		LocalDateTime eventStartDate, DivingPool divingPool) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT events.event_id AS eventId, ");
		sql.append("events.event_start_date AS eventStartDate, ");
		sql.append("events.event_end_date AS eventEndDate, ");
		sql.append("CASE WHEN likeMapping.event_id IS NOT NULL THEN TRUE ELSE FALSE END AS isLiked, ");
		sql.append("like_count.like_count AS likeCount, ");
		sql.append("SUBSTRING(events.comment,1,100) AS comment, ");
		sql.append("events.freediving_level AS freedivingLevel, ");
		sql.append("events.status AS status, ");
		sql.append("events.participant_count AS participantCount, ");
		sql.append("COUNT(DISTINCT requests.user_id) AS participantCountDistinct, ");
		sql.append("events.gender_type AS gender_type, ");
		sql.append("events.user_id AS userId ");
		sql.append("FROM buddy_event AS events ");
		sql.append("LEFT JOIN buddy_event_diving_pool_mapping AS pool ON events.event_id = pool.event_id ");
		sql.append("LEFT JOIN buddy_event_like_count AS like_count ON events.event_id = like_count.event_id ");
		sql.append(
			"LEFT JOIN buddy_event_like_mapping AS likeMapping ON events.event_id = likeMapping.event_id AND likeMapping.user_id = :userId AND likeMapping.is_deleted = false ");
		sql.append(
			"LEFT JOIN buddy_event_join_requests AS requests ON events.event_id = requests.event_id  AND requests.status in (");
		sql.append("'" + ParticipationStatus.OWNER.name() + "','" + ParticipationStatus.PARTICIPATING.name() + "') ");
		sql.append("WHERE events.event_start_date >= :startDate ");
		sql.append("AND events.status = 'RECRUITING' ");

		sql.append("AND pool.diving_pool_id = :divingPool ");

		sql.append(
			"GROUP BY events.event_id, events.event_start_date, events.event_end_date, likeMapping.event_id, like_count.like_count, events.comment, events.freediving_level, events.status, events.participant_count ");

		sql.append("ORDER BY events.event_start_date ASC ");

		sql.append("LIMIT :limit OFFSET :offset");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("userId", userId);
		query.setParameter("startDate", eventStartDate);
		query.setParameter("divingPool", divingPool.name());

		query.setParameter("limit", 10);
		query.setParameter("offset", 0);

		List<Object[]> resultList = query.getResultList();

		List<GetBuddyEventCarouselQueryProjectionDto> events = resultList.stream()
			.map(product -> new GetBuddyEventCarouselQueryProjectionDto(
				product[0] != null ? ((Number)product[0]).longValue() : null, // eventId
				convertToLocalDateTime((Timestamp)product[1]), // eventStartDate
				convertToLocalDateTime((Timestamp)product[2]), // eventEndDate
				product[3] != null ? (Boolean)product[3] : false, // isLiked
				product[4] != null ? ((Number)product[4]).longValue() : 0, // likedCount
				product[5] != null ? (String)product[5] : "", // comment
				product[6] != null ? ((Number)product[6]).longValue() : null, // freedivingLevel
				product[7] != null ? BuddyEventStatus.valueOf((String)product[7]) : BuddyEventStatus.RECRUITMENT_CLOSED,
				// status
				product[8] != null ? ((Number)product[8]).longValue() : 0, // participantCount
				product[9] != null ? ((Number)product[9]).longValue() : 0,// currentParticipantCount
				product[10] != null ? GenderType.valueOf((String)product[10]) : GenderType.ALL,
				product[11] != null ? ((Number)product[11]).longValue() : -1))// userId
			.collect(Collectors.toList());

		return events;
	}

	@Override
	public List<GetBuddyEventCarouselQueryProjectionDto> getHomeActiveBuddyEvent(Long userId,
		LocalDateTime eventStartDate, int pageNumber,
		int offset) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT events.event_id AS eventId, ");
		sql.append("events.event_start_date AS eventStartDate, ");
		sql.append("events.event_end_date AS eventEndDate, ");
		sql.append("CASE WHEN likeMapping.event_id IS NOT NULL THEN TRUE ELSE FALSE END AS isLiked, ");
		sql.append("like_count.like_count AS likeCount, ");
		sql.append("SUBSTRING(events.comment,1,100) AS comment, ");
		sql.append("events.freediving_level AS freedivingLevel, ");
		sql.append("events.status AS status, ");
		sql.append("events.participant_count AS participantCount, ");
		sql.append("COUNT(DISTINCT requests.user_id) AS participantCountDistinct, ");
		sql.append("events.gender_type AS gender_type, ");
		sql.append("events.user_id AS userId ");
		sql.append("FROM buddy_event AS events ");
		sql.append("LEFT JOIN buddy_event_diving_pool_mapping AS pool ON events.event_id = pool.event_id ");
		sql.append("LEFT JOIN buddy_event_like_count AS like_count ON events.event_id = like_count.event_id ");
		sql.append(
			"LEFT JOIN buddy_event_like_mapping AS likeMapping ON events.event_id = likeMapping.event_id AND likeMapping.user_id = :userId AND likeMapping.is_deleted = false ");
		sql.append(
			"LEFT JOIN buddy_event_join_requests AS requests ON events.event_id = requests.event_id  AND requests.status in (");
		sql.append("'" + ParticipationStatus.OWNER.name() + "','" + ParticipationStatus.PARTICIPATING.name() + "') ");
		sql.append("WHERE events.event_start_date >= :startDate ");
		sql.append("AND events.status = 'RECRUITING' ");
		sql.append(
			"GROUP BY events.event_id, events.event_start_date, events.event_end_date, likeMapping.event_id, like_count.like_count, events.comment, events.freediving_level, events.status, events.participant_count ");

		sql.append("ORDER BY events.event_start_date ASC ");

		sql.append("LIMIT :limit OFFSET :offset");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("userId", userId);

		query.setParameter("startDate", eventStartDate);
		query.setParameter("limit", offset);
		query.setParameter("offset", (pageNumber - 1) * offset);

		List<Object[]> resultList = query.getResultList();

		List<GetBuddyEventCarouselQueryProjectionDto> events = resultList.stream()
			.map(product -> new GetBuddyEventCarouselQueryProjectionDto(
				product[0] != null ? ((Number)product[0]).longValue() : null, // eventId
				convertToLocalDateTime((Timestamp)product[1]), // eventStartDate
				convertToLocalDateTime((Timestamp)product[2]), // eventEndDate
				product[3] != null ? (Boolean)product[3] : false, // isLiked
				product[4] != null ? ((Number)product[4]).longValue() : 0, // likedCount
				product[5] != null ? (String)product[5] : "", // comment
				product[6] != null ? ((Number)product[6]).longValue() : null, // freedivingLevel
				product[7] != null ? BuddyEventStatus.valueOf((String)product[7]) : BuddyEventStatus.RECRUITMENT_CLOSED,
				// status
				product[8] != null ? ((Number)product[8]).longValue() : 0, // participantCount
				product[9] != null ? ((Number)product[9]).longValue() : 0,// currentParticipantCount
				product[10] != null ? GenderType.valueOf((String)product[10]) : GenderType.ALL,
				product[11] != null ? ((Number)product[11]).longValue() : -1))// userId
			.collect(Collectors.toList());

		return events;
	}

}
