package com.freediving.communityservice.adapter.out.dto.article;

import com.freediving.communityservice.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleContent {
	private final Article article;
}
