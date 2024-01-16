package com.freediving.communityservice.application.port.in;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class BoardReadCommand extends SelfValidating<BoardReadCommand> {

	// TODO 활성화 된 게시판만 보여줄 것인지
	@NotNull
	private final boolean isEnabledOnly;

	public BoardReadCommand(boolean isEnabledOnly) {
		this.isEnabledOnly = isEnabledOnly;
		this.validateSelf();
	}
}
