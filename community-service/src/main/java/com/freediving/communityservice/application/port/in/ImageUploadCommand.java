package com.freediving.communityservice.application.port.in;

import java.util.List;

import com.freediving.common.SelfValidating;
import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.application.port.in.dto.ImageInfoCommand;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageUploadCommand extends SelfValidating<ImageUploadCommand> {

	@NotNull
	private final UserProvider userProvider;

	@NotNull
	private final BoardType boardType;

	@NotNull
	private final Long articleId;

	private final List<ImageInfoCommand> images;

	public ImageUploadCommand(UserProvider userProvider, BoardType boardType, Long articleId,
		List<ImageInfoCommand> images) {
		this.userProvider = userProvider;
		this.boardType = boardType;
		this.articleId = articleId;
		this.images = images;
		this.validateSelf();
	}
}
