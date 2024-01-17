package com.freediving.communityservice.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardWriteRequest {

	private String boardType;

	private String boardName;

	private String description;

	private int sortOrder;
}
