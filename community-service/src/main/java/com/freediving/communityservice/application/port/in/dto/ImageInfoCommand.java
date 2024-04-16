package com.freediving.communityservice.application.port.in.dto;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ImageInfoCommand extends SelfValidating<ImageInfoCommand> {

	@Min(1)
	private int sortNumber;

	@NotNull
	@Size(min = 10, max = 2000)
	private String url;

	@Builder
	public ImageInfoCommand(int sortNumber, String url) {
		this.sortNumber = sortNumber;
		this.url = url;
		this.validateSelf();
	}
}
