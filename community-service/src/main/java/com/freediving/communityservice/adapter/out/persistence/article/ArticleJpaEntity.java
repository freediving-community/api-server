package com.freediving.communityservice.adapter.out.persistence.article;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;
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
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // null 인 값은 제외하고 Insert. DB DefaultValue 사용을 위함.
@EntityListeners(AuditingEntityListener.class)
@Table(name = "article", indexes = {@Index(name = "idx_article_title", columnList = "title"),
	/* @Index(name = "idx_article_createdAt", columnList = "createdAt"), == PK desc */
	@Index(name = "idx_article_viewCount", columnList = "viewCount"),
	@Index(name = "idx_article_likeCount", columnList = "likeCount")
	/* ,@Index(name = "idx_article_hashtags", columnList = "hashtags") */})
@Entity
public class ArticleJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long articleId;

	@Column(nullable = false, length = 50)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private Long boardId;

	@Column(nullable = false, length = 20)
	private String authorName;

	// @ToString.Exclude
	// @JoinColumn(name = "hashtagId")
	// @ManyToOne
	// private List<HashtagJpaEntity> hashtags;

	@Column(nullable = false)
	private int viewCount;

	@Column(nullable = false)
	private int likeCount;

	@Column(nullable = false)
	private int commentCount;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean enableComment;

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

	public static ArticleJpaEntity of(String title, String content, Long boardId, String authorName,
		boolean enableComment) {
		return new ArticleJpaEntity(null, title, content, boardId, authorName, 0, 0, 0, enableComment,
			true,
			null, null, null, null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ArticleJpaEntity articleJpaEntity))
			return false;
		return articleId != null && articleId.equals(articleJpaEntity.articleId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleId);
	}
}




