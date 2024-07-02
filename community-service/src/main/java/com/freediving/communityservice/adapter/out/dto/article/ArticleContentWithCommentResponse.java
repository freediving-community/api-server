package com.freediving.communityservice.adapter.out.dto.article;

import java.time.LocalDateTime;
import java.util.List;

import com.freediving.communityservice.adapter.out.dto.comment.CommentResponse;
import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;
import com.freediving.communityservice.adapter.out.dto.user.UserInfo;
import com.freediving.communityservice.domain.Article;

import lombok.Data;

@Data
public class ArticleContentWithCommentResponse {

	private Long articleId;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private Long createdBy;
	private int viewCount;
	private int likeCount;
	private int commentCount;
	private final UserInfo userInfo;
	private final List<ImageResponse> images;
	private final int allCommentCount;
	private final List<CommentResponse> comments;
	private final boolean isLiked;

	public ArticleContentWithCommentResponse(Long articleId, String title, String content, LocalDateTime createdAt,
		Long createdBy, int viewCount, int likeCount, int commentCount, UserInfo userInfo, List<ImageResponse> images,
		int allCommentCount, List<CommentResponse> comments, boolean isLiked) {
		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.userInfo = userInfo;
		this.images = images;
		this.allCommentCount = allCommentCount;
		this.comments = comments;
		this.isLiked = isLiked;
	}

	public static ArticleContentWithCommentResponse of(Article article, UserInfo userInfo, List<ImageResponse> images,
		int allCommentCount, List<CommentResponse> comments,
		boolean isLiked) {

		return new ArticleContentWithCommentResponse(
			article.getId(),
			article.getTitle(),
			article.getContent(),
			article.getCreatedAt(),
			article.getCreatedBy(),
			article.getViewCount(),
			article.getLikeCount(),
			article.getCommentCount(),
			userInfo, images, allCommentCount, comments, isLiked);
	}
}
