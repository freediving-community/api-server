package com.freediving.communityservice.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "article", indexes = {
	@Index(name = "idx_article_title", columnList = "title"),
	@Index(name = "idx_article_createdAt", columnList = "createdAt"),
	@Index(name = "idx_article_viewCount", columnList = "viewCount"),
	@Index(name = "idx_article_likeCount", columnList = "likeCount"),
	@Index(name = "idx_article_hashtags", columnList = "hashtags")
})
@DynamicInsert // null 인 값은 제외하고 Insert. DB DefaultValue 사용을 위함.
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long articleId;

	@Column(nullable = false, length = 255)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false, length = 20)
	private Long boardId;

	@Column(nullable = false, length = 20)
	private String authorName;

	@ToString.Exclude
	@OneToMany
	private List<HashtagJpaEntity> hashtags = new ArrayList<>();

	@Column(nullable = false, length = 20)
	private int viewCount;

	@Column(nullable = false, length = 20)
	private int likeCount;

	@ColumnDefault("'true'")
	@Column(nullable = false, length = 10)
	private String enableComment;

	// @OrderBy("id")
	@OneToMany(mappedBy = "commentId", cascade = CascadeType.ALL)
	@ToString.Exclude
	private final Set<CommentJpaEntity> commentJpaEntities = new LinkedHashSet<>();

	// Auditing
	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private String createdBy;

	@Column(nullable = false)
	@LastModifiedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime modifiedAt;

	@Column(nullable = false)
	@LastModifiedBy
	private String modifiedBy;

	protected ArticleJpaEntity() {
	}

	private ArticleJpaEntity(Long boardId, String title, String content) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
	}

	public static ArticleJpaEntity of(Long boardId, String title, String content) {
		return new ArticleJpaEntity(boardId, title, content);
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




