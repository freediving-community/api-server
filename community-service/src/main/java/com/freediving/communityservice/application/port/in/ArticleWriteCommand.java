package com.freediving.communityservice.application.port.in;

import java.util.List;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleWriteCommand extends SelfValidating<ArticleWriteCommand> {

	private final UserProvider userProvider;

	@NotNull
	private final BoardType boardType;

	@NotBlank(message = "제목을 입력해주세요.")
	@Size(min = 2, max = 255, message = "제목은 2~255자로 입력해주세요.")
	private final String title;

	@NotBlank(message = "내용을 입력해주세요.")
	private final String content;

	private final List<Long> hashtagIds;

	private final boolean enableComment;

	private final List<ImageInfoCommand> images;

	public ArticleWriteCommand(UserProvider userProvider, BoardType boardType, String title, String content,
		List<Long> hashtagIds, boolean enableComment, List<ImageInfoCommand> images) {
		this.userProvider = userProvider;
		this.boardType = boardType;
		this.title = title;
		this.content = content;
		this.hashtagIds = hashtagIds;
		this.enableComment = enableComment;
		this.images = images;
		this.validateSelf();
	}
}
