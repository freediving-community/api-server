package com.freediving.communityservice.adapter.out.dto.article;

import java.util.List;

import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Comment;

import lombok.Data;

@Data
public class ArticleContentWithComment extends ArticleContent {

	private final List<Comment> comments;
	private final boolean isLiked;

	public ArticleContentWithComment(Article article, List<Comment> comments, boolean isLiked) {
		super(article);
		this.comments = comments;
		this.isLiked = isLiked;
	}

}
