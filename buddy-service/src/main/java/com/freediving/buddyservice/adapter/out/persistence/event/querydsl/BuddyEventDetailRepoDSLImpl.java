package com.freediving.buddyservice.adapter.out.persistence.event.querydsl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BuddyEventDetailRepoDSLImpl implements BuddyEventDetailRepoDSL {

	private final EntityManager entityManager;

	@Override
	public BuddyEventDetailQueryProjectionDto getBuddyEventDetail(Long userId, Long eventId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  events.user_id AS userId, ");
		sql.append("events.event_id AS eventId, ");
		sql.append("events.event_start_date AS eventStartDate, ");
		sql.append("events.event_end_date AS eventEndDate, ");
		sql.append("events.gender_type AS gender_type, ");
		sql.append("events.freediving_level AS freedivingLevel, ");
		sql.append("events.participant_count AS participantCount, ");
		sql.append("COUNT(DISTINCT requests.user_id) AS participantCountDistinct, ");
		sql.append("CASE WHEN likeMapping.event_id IS NOT NULL THEN TRUE ELSE FALSE END AS isLiked, ");
		sql.append("like_count.like_count AS likeCount, ");
		sql.append("view_count.view_count AS viewCount, ");
		sql.append("events.image_url AS image_url, ");
		sql.append("events.comment AS comment, ");
		sql.append("events.status AS status, ");
		sql.append("events.car_share_yn AS carShareYn ");
		sql.append("FROM buddy_event AS events ");
		sql.append("LEFT JOIN buddy_event_like_count AS like_count ON events.event_id = like_count.event_id ");
		sql.append("LEFT JOIN buddy_event_view_count AS view_count ON events.event_id = view_count.event_id ");
		sql.append(
			"LEFT JOIN buddy_event_like_mapping AS likeMapping ON events.event_id = likeMapping.event_id AND likeMapping.user_id = :userId AND likeMapping.is_deleted = false ");
		sql.append(
			"LEFT JOIN buddy_event_join_requests AS requests ON events.event_id = requests.event_id  AND requests.status in (");
		sql.append("'" + ParticipationStatus.OWNER.name() + "','" + ParticipationStatus.PARTICIPATING.name() + "') ");
		sql.append("WHERE events.event_id = :eventId ");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("userId", userId);
		query.setParameter("eventId", eventId);

		List<Object[]> resultList = query.getResultList();

		if (resultList == null || resultList.isEmpty())
			return null;

		Object[] row = resultList.get(0);
		BuddyEventDetailQueryProjectionDto event = new BuddyEventDetailQueryProjectionDto(
			row[0] != null ? ((Number)row[0]).longValue() : null, // userId
			row[1] != null ? ((Number)row[1]).longValue() : null, // eventId
			convertToLocalDateTime((Timestamp)row[2]), // eventStartDate
			convertToLocalDateTime((Timestamp)row[3]), // eventEndDate
			row[4] != null ? GenderType.valueOf((String)row[4]) : GenderType.ALL, // genderType
			row[5] != null ? ((Number)row[5]).longValue() : null, // freedivingLevel
			row[6] != null ? ((Number)row[6]).longValue() : 0, // participantCount
			row[7] != null ? ((Number)row[7]).longValue() : 0,// currentParticipantCount
			row[8] != null ? (Boolean)row[8] : false, // isLiked
			row[9] != null ? ((Number)row[9]).longValue() : 0, // likedCount
			row[10] != null ? ((Number)row[10]).longValue() : 0, // viewCount
			row[11] != null ? (String)row[11] : "", // imageUrl
			row[12] != null ? (String)row[12] : "", // comment
			row[13] != null ? BuddyEventStatus.valueOf((String)row[13]) : BuddyEventStatus.RECRUITMENT_CLOSED,// status
			row[14] != null ? (Boolean)row[14] : false // carShareYn
		);// genderType

		return event;
	}

	private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}

}
