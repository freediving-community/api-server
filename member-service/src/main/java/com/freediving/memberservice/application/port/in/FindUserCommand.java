package com.freediving.memberservice.application.port.in;

import com.freediving.common.SelfValidating;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindUserCommand extends SelfValidating<FindUserCommand> {
	private final Long userId;
}
