package com.freediving.communityservice.application.port.in;

import java.util.List;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleEditCommand extends SelfValidating<ArticleEditCommand> {

	private final UserProvider userProvider;

	@NotNull
	private final Long boardId;

	@NotNull
	private final Long articleId;

	@NotBlank(message = "제목을 입력해주세요.")
	private final String title;

	@NotBlank(message = "내용을 입력해주세요.")
	private final String content;

	private final List<Long> hashtagIds;

	private final boolean enableComment;

	public ArticleEditCommand(UserProvider userProvider, Long boardId, Long articleId, String title, String content, List<Long> hashtagIds,	boolean enableComment) {
		this.userProvider = userProvider;
		this.boardId = boardId;
		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.hashtagIds = hashtagIds;
		this.enableComment = enableComment;
		this.validateSelf();
	}
}
