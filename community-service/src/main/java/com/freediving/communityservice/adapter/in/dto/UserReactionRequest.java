package com.freediving.communityservice.adapter.in.dto;

import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "게시글에 대한 사용자 반응(좋아요)")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserReactionRequest {

	@Schema(description = "사용자 좋아요 반응", example = "LIKE")
	private UserReactionType userReactionType;

}
