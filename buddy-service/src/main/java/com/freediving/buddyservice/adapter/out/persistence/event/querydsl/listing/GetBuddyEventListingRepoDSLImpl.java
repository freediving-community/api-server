package com.freediving.buddyservice.adapter.out.persistence.event.querydsl.listing;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.adapter.out.persistence.concept.QBuddyEventConceptJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.concept.QBuddyEventConceptMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.divingpool.QBuddyEventDivingPoolMappingJpaEntity;
import com.freediving.buddyservice.adapter.out.persistence.event.join.QBuddyEventJoinRequestJpaEntity;
import com.freediving.buddyservice.config.enumerate.GenderType;
import com.freediving.buddyservice.config.enumerate.SortType;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.buddyservice.domain.enumeration.BuddyEventStatus;
import com.freediving.buddyservice.domain.enumeration.ParticipationStatus;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.divingpool.data.dao.QDivingPoolJpaEntity;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class GetBuddyEventListingRepoDSLImpl implements GetBuddyEventListingRepoDSL {

	private final EntityManager entityManager;

	@Override
	public List<GetBuddyEventListingQueryProjectionDto> getBuddyEventListing(Long userId, LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, SortType sortType, GenderType genderType, int pageNumber,
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
		sql.append("events.gender_type AS gender_type ");
		sql.append("FROM buddy_event AS events ");
		sql.append("LEFT JOIN buddy_event_diving_pool_mapping AS pool ON events.event_id = pool.event_id ");
		sql.append("LEFT JOIN buddy_event_concept_mapping AS concept ON events.event_id = concept.event_id ");
		sql.append("LEFT JOIN buddy_event_like_count AS like_count ON events.event_id = like_count.event_id ");
		sql.append(
			"LEFT JOIN buddy_event_like_mapping AS likeMapping ON events.event_id = likeMapping.event_id AND likeMapping.user_id = :userId AND likeMapping.is_deleted = false ");
		sql.append(
			"LEFT JOIN buddy_event_join_requests AS requests ON events.event_id = requests.event_id  AND requests.status in (");
		sql.append("'" + ParticipationStatus.OWNER.name() + "','" + ParticipationStatus.PARTICIPATING.name() + "') ");
		sql.append("WHERE events.event_start_date BETWEEN :startDate AND :endDate ");
		sql.append("AND events.status = 'RECRUITING' ");

		if (genderType.equals(GenderType.ALL) == false)
			sql.append(" AND events.gender_type = :genderType ");

		if (carShareYn != null) {
			sql.append("AND events.car_share_yn = :carShareYn ");
		}

		if (freedivingLevel != null) {
			sql.append("AND events.freediving_level = :freedivingLevel ");
		}

		if (divingPools != null && !divingPools.isEmpty()) {
			sql.append("AND pool.diving_pool_id = ANY(:divingPools) ");
		}

		if (buddyEventConcepts != null && !buddyEventConcepts.isEmpty()) {
			sql.append("AND EXISTS ( ");
			sql.append("   SELECT 1 FROM buddy_event_concept_mapping becm ");
			sql.append("   WHERE becm.event_id = events.event_id ");
			sql.append("   GROUP BY becm.event_id ");
			sql.append(
				"   HAVING SUM(CASE WHEN becm.concept_id = ANY(:buddyEventConcepts) THEN 1 ELSE 0 END) = :conceptCount ) ");
		}

		sql.append(
			"GROUP BY events.event_id, events.event_start_date, events.event_end_date, likeMapping.event_id, like_count.like_count, events.comment, events.freediving_level, events.status, events.participant_count ");

		switch (sortType) {
			case POPULARITY:
				sql.append("ORDER BY like_count.like_count DESC ");
				break;
			case DEADLINE:
				sql.append("ORDER BY events.event_end_date ASC ");
				break;
			case NEWEST:
			default:
				sql.append("ORDER BY events.event_start_date DESC ");
				break;
		}

		sql.append("LIMIT :limit OFFSET :offset");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("userId", userId);
		query.setParameter("startDate", eventStartDate);
		query.setParameter("endDate", eventEndDate);

		if (carShareYn != null) {
			query.setParameter("carShareYn", carShareYn);
		}

		if (freedivingLevel != null) {
			query.setParameter("freedivingLevel", freedivingLevel);
		}

		if (divingPools != null && !divingPools.isEmpty()) {
			query.setParameter("divingPools", divingPools.stream().map(Enum::name).toArray(String[]::new));
		}

		if (buddyEventConcepts != null && !buddyEventConcepts.isEmpty()) {
			query.setParameter("buddyEventConcepts",
				buddyEventConcepts.stream().map(Enum::name).toArray(String[]::new));
			query.setParameter("conceptCount", buddyEventConcepts.size());
		}

		if (genderType.equals(GenderType.ALL) == false)
			query.setParameter("genderType", genderType.name());

		query.setParameter("limit", offset);
		query.setParameter("offset", (pageNumber - 1) * offset);

		List<Object[]> resultList = query.getResultList();

		List<GetBuddyEventListingQueryProjectionDto> events = resultList.stream()
			.map(product -> new GetBuddyEventListingQueryProjectionDto(
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
				product[10] != null ? GenderType.valueOf((String)product[10]) : GenderType.ALL))// genderType
			.collect(Collectors.toList());

		return events;
	}

	private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}

	@Override
	public Long countOfGetBuddyEventListing(Long userId,
		LocalDateTime eventStartDate,
		LocalDateTime eventEndDate, Set<BuddyEventConcept> buddyEventConcepts, Boolean carShareYn,
		Integer freedivingLevel, Set<DivingPool> divingPools, GenderType genderType) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT events.event_id) ");
		sql.append("FROM buddy_event AS events ");
		sql.append("WHERE events.event_start_date BETWEEN :startDate AND :endDate ");
		sql.append("AND events.status = 'RECRUITING' ");

		if (carShareYn != null) {
			sql.append("AND events.car_share_yn = :carShareYn ");
		}

		if (genderType.equals(GenderType.ALL) == false)
			sql.append(" AND events.gender_type = :genderType ");

		if (freedivingLevel != null) {
			sql.append("AND events.freediving_level = :freedivingLevel ");
		}

		if (divingPools != null && !divingPools.isEmpty()) {
			sql.append("AND EXISTS ( ");
			sql.append("    SELECT 1 FROM buddy_event_diving_pool_mapping pool ");
			sql.append("    WHERE pool.event_id = events.event_id ");
			sql.append("    AND pool.diving_pool_id = ANY(:divingPools) ");
			sql.append(") ");
		}

		if (buddyEventConcepts != null && !buddyEventConcepts.isEmpty()) {
			sql.append("AND EXISTS ( ");
			sql.append("    SELECT 1 FROM buddy_event_concept_mapping becm ");
			sql.append("    WHERE becm.event_id = events.event_id ");
			sql.append("    GROUP BY becm.event_id ");
			sql.append(
				"    HAVING SUM(CASE WHEN becm.concept_id = ANY(:buddyEventConcepts) THEN 1 ELSE 0 END) = :conceptCount ");
			sql.append(") ");
		}

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startDate", eventStartDate);
		query.setParameter("endDate", eventEndDate);

		if (genderType.equals(GenderType.ALL) == false)
			query.setParameter("genderType", genderType.name());

		if (carShareYn != null) {
			query.setParameter("carShareYn", carShareYn);
		}

		if (freedivingLevel != null) {
			query.setParameter("freedivingLevel", freedivingLevel);
		}

		if (divingPools != null && !divingPools.isEmpty()) {
			query.setParameter("divingPools", divingPools.stream().map(Enum::name).toArray(String[]::new));
		}

		if (buddyEventConcepts != null && !buddyEventConcepts.isEmpty()) {
			query.setParameter("buddyEventConcepts",
				buddyEventConcepts.stream().map(Enum::name).toArray(String[]::new));
			query.setParameter("conceptCount", buddyEventConcepts.size());
		}

		return ((Number)query.getSingleResult()).longValue();
	}

	@Override
	public Map<Long, List<BuddyEventConceptMappingProjectDto>> findConceptMappingAllByEventIds(List<Long> ids) {
		QBuddyEventConceptMappingJpaEntity qConceptMapping = QBuddyEventConceptMappingJpaEntity.buddyEventConceptMappingJpaEntity;
		QBuddyEventConceptJpaEntity qBuddyEventConcept = QBuddyEventConceptJpaEntity.buddyEventConceptJpaEntity;

		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);

		List<BuddyEventConceptMappingProjectDto> results = jpqlQueryFactory
			.select(new QBuddyEventConceptMappingProjectDto(
				qConceptMapping.buddyEvent.eventId,
				qConceptMapping.conceptId,
				qBuddyEventConcept.conceptName
			))
			.from(qConceptMapping)
			.innerJoin(qBuddyEventConcept)
			.on(qConceptMapping.conceptId.eq(qBuddyEventConcept.conceptId))
			.where(qConceptMapping.buddyEvent.eventId.in(ids))
			.fetch();

		return results.stream()
			.collect(Collectors.groupingBy(BuddyEventConceptMappingProjectDto::getEventId));
	}

	@Override
	public Map<Long, List<BuddyEventDivingPoolMappingProjectDto>> findDivingPoolMappingAllByEventIds(List<Long> ids) {
		QBuddyEventDivingPoolMappingJpaEntity qDivingPoolMapping = QBuddyEventDivingPoolMappingJpaEntity.buddyEventDivingPoolMappingJpaEntity;
		QDivingPoolJpaEntity qDivingPool = QDivingPoolJpaEntity.divingPoolJpaEntity;
		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);

		List<BuddyEventDivingPoolMappingProjectDto> results = jpqlQueryFactory
			.select(new QBuddyEventDivingPoolMappingProjectDto(qDivingPoolMapping.buddyEvent.eventId,
				qDivingPool.divingPoolId,
				qDivingPool.divingPoolName
			))
			.from(qDivingPoolMapping)
			.innerJoin(qDivingPool)
			.on(qDivingPoolMapping.divingPoolId.eq(qDivingPool.divingPoolId))
			.where(qDivingPoolMapping.buddyEvent.eventId.in(ids))
			.fetch();

		return results.stream()
			.collect(Collectors.groupingBy(BuddyEventDivingPoolMappingProjectDto::getEventId));
	}

	@Override
	public Map<Long, List<BuddyEventJoinMappingProjectDto>> findJoinMappingAllByEventIds(List<Long> ids) {
		QBuddyEventJoinRequestJpaEntity qBuddyEventJoin = QBuddyEventJoinRequestJpaEntity.buddyEventJoinRequestJpaEntity;
		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);

		List<BuddyEventJoinMappingProjectDto> results = jpqlQueryFactory
			.select(new QBuddyEventJoinMappingProjectDto(qBuddyEventJoin.buddyEvent.eventId,
				qBuddyEventJoin.userId,
				qBuddyEventJoin.status
			))
			.from(qBuddyEventJoin)
			.where(qBuddyEventJoin.buddyEvent.eventId.in(ids))
			.fetch();

		return results.stream()
			.collect(Collectors.groupingBy(BuddyEventJoinMappingProjectDto::getEventId));
	}
}
