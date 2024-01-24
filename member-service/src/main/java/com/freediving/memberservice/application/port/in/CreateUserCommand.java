package com.freediving.memberservice.application.port.in;

import com.freediving.common.SelfValidating;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.valid.EnumValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/16
 * @Description    : CreateUserRequest 정보를 Command 객체로 전환 및 SelfValidation
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/16        sasca37       최초 생성
 */
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateUserCommand extends SelfValidating<CreateUserCommand> {

	@EnumValid(enumClass = OauthType.class)
	private OauthType oauthType;

	@NotNull
	@Email
	private String email;

	private String profileImgUrl;

	public CreateUserCommand(OauthType oauthType, String email, String profileImgUrl) {
		this.oauthType = oauthType;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.validateSelf();
	}
}
