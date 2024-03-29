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
public class ArticleEditRequest {
	private String title;

	private String content;

	private List<Long> hashtagIds;

	private boolean enableComment;

}
