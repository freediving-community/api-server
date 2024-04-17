package com.freediving.buddyservice.application.port.in.web.command.like;

/**
 * BuddyEventLikeToggleUseCase 버디 이벤트 좋아요 액션의 커멘드
 * 설정과, 해지를 가지고 있다.
 *
 * @author pus__
 * @version 1.0.0
 * 작성일 2024-04-10
 **/
public interface BuddyEventLikeToggleUseCase {

	/**
	 * 버디 이벤트 좋아요 설정/해지의 액션의 서비스
	 *
	 * @param command 버디 이벤트 좋아요 설정/해지 명령 객체
	 * @return desc
	 */
	void buddyEventLikeToggle(BuddyEventLikeToggleCommand command);

}
