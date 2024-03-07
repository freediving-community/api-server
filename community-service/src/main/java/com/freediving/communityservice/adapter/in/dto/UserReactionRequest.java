package com.freediving.communityservice.adapter.in.dto;

import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserReactionRequest {

	private UserReactionType userReactionType;

}
