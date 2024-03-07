package com.freediving.communityservice.adapter.out.persistence.likearticle;

import com.freediving.communityservice.adapter.out.persistence.article.ArticleJpaEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_like")
@Entity
public class LikeArticleJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@JoinColumn(name = "article_id")
	@ManyToOne
	private ArticleJpaEntity articleId;

	private Long userId;
}
