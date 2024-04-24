package com.freediving.communityservice.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "댓글 삭제 요청")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDeleteRequest {

	@Schema(description = "답글인 경우 댓글의 ID", example = "1234 혹은 null")
	private Long parentId;

}
