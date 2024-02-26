package com.freediving.communityservice.adapter.out.persistence.comment;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class CommentJpaEntity {
	//TODO articleId가 필수임. 게시물처럼 커서 방식을 적용. hasChild와 parentId를 기반으로 답글 기능 포함.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(nullable = false)
	private Long articleId;

	@Column
	private Long parentId;

	@Column(nullable = false, length = 500)
	private String content;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean visible;

	// Auditing
	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private Long createdBy;

	@Column
	@LastModifiedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime modifiedAt;

	@Column
	@LastModifiedBy
	private Long modifiedBy;

	@Builder
	public CommentJpaEntity(Long articleId, Long parentId, String content, boolean visible, Long createdBy,
		LocalDateTime modifiedAt, Long modifiedBy) {
		this.articleId = articleId;
		this.parentId = parentId;
		this.content = content;
		this.visible = visible;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CommentJpaEntity commentJpaEntity))
			return false;
		return commentId != null && Objects.equals(commentId, commentJpaEntity.commentId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentId);
	}
}
