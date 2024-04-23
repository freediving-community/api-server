package com.freediving.communityservice.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "업로드된 이미지 정보")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleImageRequest {
	@Schema(description = "사진 순서 1부터 시작.", example = "1")
	private int sortNumber;

	@Schema(description = "이미지 CDN URL", example = "https://cdn.aws.com:123/asdf/images/123qwe456asd")
	private String url;

	// private int size;
	//
	// private String fileName;
	//
	// private String extension;
}
