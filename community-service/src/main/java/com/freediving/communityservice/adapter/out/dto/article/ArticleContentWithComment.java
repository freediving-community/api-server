package com.freediving.communityservice.adapter.out.dto.article;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleContentWithComment {
	private  Long articleId;
	private  String title;
	private  String content;
	private  String authorName;
	private  int viewCount;
	private  int likeCount;
	private  boolean enableComment;
	private  LocalDateTime createdAt;
	private  Long createdBy;
	private  LocalDateTime modifiedAt;
	private  Long modifiedBy;

	private  Long commentId;
	private  Long parentId;
	private  String commentContent;
	private  boolean commentVisible;
	private  LocalDateTime commentCreatedAt;
	private  Long commentCreatedBy;
	private  LocalDateTime commentModifiedAt;
	private  Long commentModifiedBy;

	@QueryProjection
	public ArticleContentWithComment(Long articleId, String title, String content, String authorName, int viewCount,
		int likeCount, boolean enableComment, LocalDateTime createdAt, Long createdBy, LocalDateTime modifiedAt,
		Long modifiedBy, Long commentId, Long parentId, String commentContent, boolean commentVisible,
		LocalDateTime commentCreatedAt, Long commentCreatedBy, LocalDateTime commentModifiedAt,
		Long commentModifiedBy) {
		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.authorName = authorName;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.enableComment = enableComment;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
		this.commentId = commentId;
		this.parentId = parentId;
		this.commentContent = commentContent;
		this.commentVisible = commentVisible;
		this.commentCreatedAt = commentCreatedAt;
		this.commentCreatedBy = commentCreatedBy;
		this.commentModifiedAt = commentModifiedAt;
		this.commentModifiedBy = commentModifiedBy;
	}
}
