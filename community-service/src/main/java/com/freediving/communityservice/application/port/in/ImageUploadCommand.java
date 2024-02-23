package com.freediving.communityservice.application.port.in;

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
public class ImageUploadCommand extends SelfValidating<ImageUploadCommand> {

	@NotNull
	private final UserProvider userProvider;

	private final Integer width;

	private final Integer height;

	private final String style;

	private final String description;

	@NotBlank
	private final String originName;

	@NotBlank
	private final String extension;

	@NotBlank
	private final Integer size;

	public ImageUploadCommand(UserProvider userProvider, Integer width, Integer height, String style,
		String description, String originName, String extension, Integer size) {
		this.userProvider = userProvider;
		this.width = width;
		this.height = height;
		this.style = style;
		this.description = description;
		this.originName = originName;
		this.extension = extension;
		this.size = size;
		this.validateSelf();
	}
}
