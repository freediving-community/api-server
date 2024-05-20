package com.freediving.memberservice.application.port.in;

import com.freediving.common.SelfValidating;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/19
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/19        sasca37       최초 생성
 */
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateUserProfileCommand extends SelfValidating<CreateUserProfileCommand> {

	private Long userId;
	private String profileImgUrl;

	private String nickname;

	private String content;
}
