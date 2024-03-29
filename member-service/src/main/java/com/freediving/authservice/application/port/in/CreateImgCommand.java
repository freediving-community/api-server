package com.freediving.authservice.application.port.in;

import com.freediving.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : CreateImgRequest 정보를 Command 객체로 전환 및 SelfValidation
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateImgCommand extends SelfValidating<CreateImgCommand> {

	@NotNull
	private Long userId;

	@NotNull
	private String directory;

	@NotNull
	private String ext;

	public CreateImgCommand(Long userId, String directory, String ext) {
		this.userId = userId;
		this.directory = directory;
		this.ext = ext;
		this.validateSelf();
	}
}
