package com.freediving.communityservice.adapter.out.dto.article;

import java.time.LocalDateTime;

import com.freediving.communityservice.adapter.out.dto.image.ImageBriefResponse;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleBriefDto {

	private Long articleId;

	private String title;

	private String content;

	private LocalDateTime createdAt;

	private Long createdBy;

	private UserInfo userInfo;

	private int viewCount;

	private int likeCount;

	private int commentCount;

	private ImageBriefResponse images;

	@Builder
	public ArticleBriefDto(Long articleId, String title, String content, LocalDateTime createdAt,
		Long createdBy, int viewCount, int likeCount, int commentCount, int sortNumber, String url,
		int imageTotalCount) {
		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.images = new ImageBriefResponse(sortNumber, url, imageTotalCount);
	}

}
