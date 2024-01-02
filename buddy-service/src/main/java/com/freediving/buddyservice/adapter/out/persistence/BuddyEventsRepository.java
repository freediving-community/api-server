package com.freediving.buddyservice.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuddyEventsRepository extends JpaRepository<BuddyEventsJpaEntity, Long> {

	/**
	 * 사용자가 생성할 이벤트의 시작 시간과 종료 시간에 이미
	 * 생성한 이벤트가 존재하는지 체크.
	 * @param userId 사용자 식별 ID
	 * @param eventStartTime 생성하려는 이벤트 시작 시간
	 * @param eventEndTime 생성하려는 이벤트 종료 시간
	 * @param statuses 체킹하려는 이벤트의 상태들 String 리스트  기본적으로 "RECRUITING", "RECRUITMENT_CLOSED" 사용.
	 * @return 이미 생성한 이벤트 존재 여부
	 */
	@Query(value =
		"SELECT EXISTS (SELECT 1 FROM buddy_events e WHERE (( e.event_start_date < :eventStartTime AND "
			+ " :eventStartTime < e.event_end_date) OR ( e.event_start_date < :eventEndTime AND "
			+ " :eventEndTime < e.event_end_date )) AND e.user_id = :userId AND e.status in (:statuses) ) ", nativeQuery = true)
	boolean existsBuddyEventByEventTime(@Param("userId") Long userId,
		@Param("eventStartTime") LocalDateTime eventStartTime,
		@Param("eventEndTime") LocalDateTime eventEndTime,
		@Param("statuses") List<String> statuses);

}
