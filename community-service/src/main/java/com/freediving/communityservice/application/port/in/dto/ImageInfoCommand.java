package com.freediving.communityservice.application.port.in.dto;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	private int size;

	private String fileName;

	@Size(min = 3, max = 4)
	private String extension;

	public ImageInfoCommand(int sortNumber, String url, int size, String fileName, String extension) {
		this.sortNumber = sortNumber;
		this.url = url;
		this.size = size;
		this.fileName = fileName;
		this.extension = extension;
		this.validateSelf();
	}
}
