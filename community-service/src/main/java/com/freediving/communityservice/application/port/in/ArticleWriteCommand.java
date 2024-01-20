package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleWriteCommand extends SelfValidating<ArticleWriteCommand> {
	// private final Long id;

	@NotNull
	private final Long boardId;

	@NotBlank(message = "제목을 입력해주세요.")
	private final String title;

	@NotBlank(message = "내용을 입력해주세요.")
	private final String content;

	@NotBlank(message = "작성자 닉네임이 없습니다.")
	private final String authorName;

	private final boolean enableComment;

	@NotNull
	private final Long createdBy;

	public ArticleWriteCommand(Long boardId, String title, String content, String authorName, boolean enableComment,
		Long createdBy) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.authorName = authorName;
		this.enableComment = enableComment;
		this.createdBy = createdBy;
		this.validateSelf();
	}
}
