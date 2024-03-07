package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CommentWriteCommand extends SelfValidating<CommentWriteCommand> {

	@NotNull
	private UserProvider requestUser;

	@NotNull
	private final BoardType boardType;

	@NotNull
	private final Long articleId;

	private final Long parentId;

	@NotBlank(message = "내용이 없습니다.")
	private final String content;

	private final boolean visible;

	public CommentWriteCommand(UserProvider userProvider, BoardType boardType, Long articleId, Long parentId,
		String content,
		boolean visible) {
		this.requestUser = userProvider;
		this.boardType = boardType;
		this.articleId = articleId;
		this.parentId = parentId;
		this.content = content;
		this.visible = visible;
		this.validateSelf();
	}

	public CommentWriteCommand applyParentVisible(CommentWriteCommand requestCommand, boolean parentVisible) {
		return CommentWriteCommand.builder()
			.requestUser(requestCommand.getRequestUser())
			.boardType(requestCommand.getBoardType())
			.articleId(requestCommand.getArticleId())
			.parentId(requestCommand.getParentId())
			.content(requestCommand.getContent())
			.visible(parentVisible)
			.build();
	}

	public boolean hasParentComment() {
		return this.parentId != null;
	}

}
