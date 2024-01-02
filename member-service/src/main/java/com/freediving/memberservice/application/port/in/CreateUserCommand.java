package com.freediving.memberservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.memberservice.domain.OauthType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateUserCommand extends SelfValidating<CreateUserCommand> {

	private OauthType oauthType;

	@NotNull
	private final String email;

	private final String profileImgUrl;

	public CreateUserCommand(OauthType oauthType, String email, String profileImgUrl) {
		this.oauthType = oauthType;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.validateSelf();
	}

}
