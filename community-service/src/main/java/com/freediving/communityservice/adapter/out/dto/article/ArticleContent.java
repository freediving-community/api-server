package com.freediving.communityservice.adapter.out.dto.article;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;
import com.freediving.communityservice.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleContent {
	//TODO 필드 전부 받고 DTO로 분리해야 함.
	private final Article article;
	private final List<ImageResponse> images;
}
