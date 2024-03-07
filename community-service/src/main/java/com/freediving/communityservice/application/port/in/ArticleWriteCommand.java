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
public class ArticleWriteCommand extends SelfValidating<ArticleWriteCommand> {

	private final UserProvider userProvider;

	@NotNull
	private final Long boardId;

	@NotBlank(message = "제목을 입력해주세요.")
	private final String title;

	@NotBlank(message = "내용을 입력해주세요.")
	private final String content;

	@NotBlank(message = "작성자 닉네임이 없습니다.")
	private final String authorName;

	private final List<Long> hashtagIds;

	private final boolean enableComment;

	public ArticleWriteCommand(UserProvider userProvider, Long boardId, String title, String content, String authorName,
		List<Long> hashtagIds,
		boolean enableComment) {
		this.userProvider = userProvider;
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.authorName = authorName;
		this.hashtagIds = hashtagIds;
		this.enableComment = enableComment;
		this.validateSelf();
	}
}
