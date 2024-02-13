package com.freediving.communityservice.domain;

import java.time.LocalDateTime;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.ArticleWriteCommand;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {
	private final Long id;

	private final BoardType boardType;

	private final String boardName;

	private final String description;

	private final int sortOrder;

	private final boolean enabled;

	private final LocalDateTime createdAt;

	private final LocalDateTime modifiedAt;

	private final Long createdBy;

	private final Long modifiedBy;

	//TODO RoleLevel을 BoardJpaEntity 추가하여 관리 등 구현
	// private final UserProvider.RoleLevel roleLevel;

	public static Board of(Long id, BoardType boardType, String boardName, String description, int sortOrder,
		boolean enabled, LocalDateTime createdAt, LocalDateTime modifiedAt, Long createdBy, Long modifiedBy) {

		return new Board(id, boardType, boardName, description, sortOrder, enabled, createdAt, modifiedAt, createdBy,
			modifiedBy);
	}

	// TODO 게시판 별 권한 체크 로직 등 설정
	public void checkPermission(ArticleWriteCommand articleWriteCommand) {
		if (false) {
			throw new IllegalArgumentException("권한이나 뭐 어쩌구가 없음");
		}
	}

	public void checkPermission(Long boardId, UserProvider userProvider) {
		/* TODO
		 * 	 - 조회 권한
		 * 	 - 조작 권한
		 * */
		if (hasPermissionLevel(userProvider)) {
			System.out.println("BOARDTYPE====>" + this.boardType.equals(BoardType.GENERAL));
		}
	}

	private boolean hasPermissionLevel(UserProvider requestUser) {
		return requestUser.getRoleLevel().getValue() >= this.boardType.getRoleLevel();
	}
}
