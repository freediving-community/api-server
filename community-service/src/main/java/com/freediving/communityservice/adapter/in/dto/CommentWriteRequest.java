package com.freediving.communityservice.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentWriteRequest {

	private Long parentId;

	private String content;

	private boolean visible;

}
