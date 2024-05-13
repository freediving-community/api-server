// package com.freediving.communityservice.adapter.out.persistence.article;
//
// import com.freediving.communityservice.adapter.out.persistence.hashtag.HashtagJpaEntity;
//
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.ToString;
//
// @Getter
// @ToString
// @NoArgsConstructor
// @AllArgsConstructor
// @Table(name = "article_hashtag")
// @Entity
// public class ArticleHashtagJpaEntity {
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.SEQUENCE)
// 	private Long id;
//
// 	@JoinColumn(name = "article_id")
// 	@ManyToOne
// 	private ArticleJpaEntity articleId;
//
// 	@JoinColumn(name = "hashtag_id")
// 	@ManyToOne
// 	private HashtagJpaEntity hashtagId;
//
// }
