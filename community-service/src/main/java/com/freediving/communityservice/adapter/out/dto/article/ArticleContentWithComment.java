package com.freediving.communityservice.adapter.out.dto.article;

import java.util.List;

import com.freediving.communityservice.domain.Article;
import com.freediving.communityservice.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleContentWithComment {
	private final Article article;
	private final List<Comment> comments;
}
