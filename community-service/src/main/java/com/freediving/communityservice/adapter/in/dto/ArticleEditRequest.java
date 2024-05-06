package com.freediving.communityservice.adapter.in.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "게시글 수정 요청")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleEditRequest {
	@Schema(description = "게시글 제목", example = "스토리 제목입니다.")
	private String title;

	@Schema(description = "게시글 본문", example = "안녕하세요. 본문 내용입니다.")
	private String content;

	@Schema(description = "작성자 닉네임", example = "테슬라주주파크")
	private String authorName;

	@Schema(description = "댓글 허용여부", example = "true")
	private boolean enableComment;

	private List<ArticleImageRequest> images;
}
