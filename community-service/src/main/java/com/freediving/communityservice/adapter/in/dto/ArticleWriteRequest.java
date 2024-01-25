package com.freediving.communityservice.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleWriteRequest {
	private String title;

	private String content;

	private String authorName;

	private boolean enableComment;

	private Long createdBy;

}
