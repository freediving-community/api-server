package com.freediving.communityservice.adapter.out.dto.article;

import java.util.List;

import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleContentWithComment extends ArticleContent {
	//TODO 필드 전부 받고 DTO로 분리해야 함.

	private final List<Comment> comments;
	private final boolean isLiked;

	public ArticleContentWithComment(Article article, List<Comment> comments, boolean isLiked) {
		super(article);
		this.comments = comments;
		this.isLiked = isLiked;
	}

}
