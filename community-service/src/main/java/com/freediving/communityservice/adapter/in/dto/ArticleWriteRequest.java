package com.freediving.communityservice.adapter.in.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "게시글 등록 요청")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleWriteRequest {
	@Schema(description = "게시글 제목", example = "스토리 제목입니다.")
	private String title;

	@Schema(description = "게시글 본문", example = "안녕하세요. 본문 내용입니다.")
	private String content;

	@Schema(description = "작성자 닉네임", example = "테슬라주주파크")
	private String authorName;

	@Schema(description = "댓글 허용여부", example = "true(기본값)")
	private boolean enableComment;

	@Schema(description = "게시글 작성자 ID", example = "12345")
	private Long createdBy;

	private List<ArticleImageRequest> images;
}
