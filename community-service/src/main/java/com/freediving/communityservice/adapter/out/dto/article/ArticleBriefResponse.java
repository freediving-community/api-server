package com.freediving.communityservice.adapter.out.dto.article;

import java.time.LocalDateTime;

import com.freediving.communityservice.adapter.out.dto.image.ImageBriefResponse;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;

import lombok.Builder;
import lombok.Data;

@Data
public class ArticleBriefResponse {

	private Long articleId;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private Long createdBy;
	private int viewCount;
	private int likeCount;
	private int commentCount;
	private UserInfo userInfo;
	private ImageBriefResponse images;

	@Builder
	public ArticleBriefResponse(Long articleId, String title, String content, LocalDateTime createdAt, Long createdBy,
		int viewCount, int likeCount, int commentCount, UserInfo userInfo, int sortNumber, String url,
		int imageTotalCount) {

		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.userInfo = userInfo;
		this.images = new ImageBriefResponse(sortNumber, url, imageTotalCount);
	}
}
