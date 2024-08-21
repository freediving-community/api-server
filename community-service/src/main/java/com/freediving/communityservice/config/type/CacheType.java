package com.freediving.communityservice.config.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

	// TODO DB혹은 properties로 만료 시간에 대한 관리.

	/*
	 * 게시물
	 * */
	ARTICLE_CREATE_TIME_LIMIT("articleCreateTimeLimit", 60, -1, 10000),
	// ,COMMENT_CREATE_TIME_LIMIT("commentCreateTimeLimit", 60, 10000);

	/*
	 * 채팅방
	 * */
	CHATROOM_ENTER_TIME("chatRoomEnterTime", 60 * 20, 60 * 20, 10000);

	private final String cacheName;
	private final int expiredSecondAfterWriteSeconds;
	private final int expireAfterAccessSeconds;
	private final int maximumSize;
}
