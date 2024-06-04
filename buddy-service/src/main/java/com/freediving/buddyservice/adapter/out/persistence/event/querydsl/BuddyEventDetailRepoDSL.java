package com.freediving.buddyservice.adapter.out.persistence.event.querydsl;

public interface BuddyEventDetailRepoDSL {

	/**
	 * 버디 매칭 상세 정보를 조회한다.
	 * @return BuddyEventDetailQueryProjectionDto 필터링 된 객체
	 */

	BuddyEventDetailQueryProjectionDto getBuddyEventDetail(Long userId, Long eventId);

}
