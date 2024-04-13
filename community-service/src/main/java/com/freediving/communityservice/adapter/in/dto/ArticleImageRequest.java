package com.freediving.communityservice.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleImageRequest {
	private int sortNumber;

	private String url;

	private int size;

	private String fileName;

	private String extension;
}
