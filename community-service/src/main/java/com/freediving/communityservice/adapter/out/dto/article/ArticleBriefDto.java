package com.freediving.communityservice.adapter.out.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBriefDto {

	private Long articleId;

	private String title;

	private String authorName;

	private Long createdBy;

	private int viewCount;

	private int likeCount;

	private int commentCount;

}
