package com.freediving.community.application.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "article", indexes = {
	@Index(columnList = "title"),
	@Index(columnList = "createdAt")
})
@DynamicInsert // null 인 값은 제외하고 Insert. DB DefaultValue 사용을 위함.
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long articleId;

	@Setter
	@Column(nullable = false, length = 255)
	private String title;

	@Setter
	@Column(nullable = false, columnDefinition="TEXT")
	private String content;

	@Setter
	@Column(nullable = false, length = 20)
	private Long boardId;

	// @ElementCollection
	// @ToString.Exclude
	// private final Set<HashTag> tags = new LinkedHashSet<>();

	@OneToMany(mappedBy = "article")
	private List<ArticleHashtag> hashtags = new ArrayList<>();

	@Setter
	@Column(nullable = false, length = 20)
	private Long memberId;

	@Setter
	@Column(nullable = false, length = 20)
	private String authorName;

	@Setter
	@ColumnDefault("'true'")
	@Column(nullable = false, length = 10)
	private String enableComment;

	// @OrderBy("id")
	@OneToMany(mappedBy = "commentId", cascade = CascadeType.ALL)
	@ToString.Exclude
	private final Set<Comment> comments = new LinkedHashSet<>();

	// Auditing
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@Column(nullable = false)
	@CreatedBy
	private String createdBy;

	@Column(nullable = false)
	@LastModifiedBy
	private String modifiedBy;

	protected Article() {
	}

	private Article(Long boardId, Long memberId, String title, String content) {
		this.boardId = boardId;
		this.memberId = memberId;
		this.title = title;
		this.content = content;
	}

	public static Article of(Long boardId, Long memberId, String title, String content) {
		return new Article(boardId, memberId, title, content);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Article article))
			return false;
		return articleId != null && articleId.equals(article.articleId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleId);
	}
}




