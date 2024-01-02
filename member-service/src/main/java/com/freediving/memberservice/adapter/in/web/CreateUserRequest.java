package com.freediving.memberservice.adapter.in.web;

import com.freediving.memberservice.domain.OauthType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

	private OauthType oauthType;
	private String email;
	private String profileImgUrl;
}
