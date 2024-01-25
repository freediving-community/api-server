package com.freediving.communityservice.adapter.out.persistence.comment;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

	// @Setter
	// @ManyToOne(optional = false)
	// private ArticleJpaEntity articleJpaEntity;

	@Setter
	@Column(nullable = false, length = 500)
	private String content;

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@CreatedBy
	private Long createdBy;

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@Column(nullable = false)
	@LastModifiedBy
	private Long modifiedBy;

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
