package com.freediving.memberservice.application.port.in;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 유저 정보 조회를 위한 Command
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindUserCommand extends SelfValidating<FindUserCommand> {

	@NotNull
	private final Long userId;
}
