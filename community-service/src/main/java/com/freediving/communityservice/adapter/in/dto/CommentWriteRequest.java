package com.freediving.communityservice.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "댓글 작성 요청")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentWriteRequest {

	@Schema(description = "답글인 경우 댓글의 ID", example = "1234 혹은 null")
	private Long parentId;

	@Schema(description = "댓글 내용", example = "알려주셔서 감사합니다.")
	private String content;

	@Schema(description = "비밀 댓글 여부", example = "true, 답글인 경우 댓글의 비밀여부를 따라감")
	private boolean visible;

}
