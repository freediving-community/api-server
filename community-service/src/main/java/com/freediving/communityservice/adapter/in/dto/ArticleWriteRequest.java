package com.freediving.communityservice.adapter.in.dto;

import java.util.List;

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

	private List<Long> hashtagIds;

	private boolean enableComment;

	private Long createdBy;

	private List<ArticleImageRequest> images;
}
