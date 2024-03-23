package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleIndexListCommand extends SelfValidating<ArticleIndexListCommand> {

	private final UserProvider userProvider;

	@NotNull
	private final BoardType boardType;

	@NotNull
	private final int page;

	@NotNull
	private final int offset;

	@NotNull
	private final String orderBy;

	private final Long cursor;

	public ArticleIndexListCommand(UserProvider userProvider, BoardType boardType, int page, int offset, String orderBy,
		Long cursor) {
		this.userProvider = userProvider;
		this.boardType = boardType;
		this.page = page - 1;
		this.offset = Math.min(offset, 100);
		this.orderBy = orderBy;
		this.cursor = cursor; //TODO cursor 구현( articleId 그대로 노출? ) 및 유효값 검사
		this.validateSelf();
	}
}
