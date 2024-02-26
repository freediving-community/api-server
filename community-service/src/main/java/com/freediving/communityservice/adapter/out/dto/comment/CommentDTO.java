// package com.freediving.communityservice.adapter.out.dto.comment;
//
// import java.time.LocalDateTime;
//
// import com.querydsl.core.annotations.QueryProjection;
//
// import lombok.Data;
// import lombok.NoArgsConstructor;
//
// @Data
// @NoArgsConstructor
// public class CommentDTO {
// 	private  Long commentId;
// 	private  Long parentId;
// 	private  String commentContent;
// 	private  boolean commentVisible;
// 	private  LocalDateTime commentCreatedAt;
// 	private  Long commentCreatedBy;
// 	private  LocalDateTime commentModifiedAt;
// 	private  Long commentModifiedBy;
//
// 	@QueryProjection
// 	public CommentDTO(Long commentId, Long parentId, String commentContent, boolean commentVisible,
// 		LocalDateTime commentCreatedAt, Long commentCreatedBy, LocalDateTime commentModifiedAt,
// 		Long commentModifiedBy) {
// 		this.commentId = commentId;
// 		this.parentId = parentId;
// 		this.commentContent = commentContent;
// 		this.commentVisible = commentVisible;
// 		this.commentCreatedAt = commentCreatedAt;
// 		this.commentCreatedBy = commentCreatedBy;
// 		this.commentModifiedAt = commentModifiedAt;
// 		this.commentModifiedBy = commentModifiedBy;
// 	}
// }
