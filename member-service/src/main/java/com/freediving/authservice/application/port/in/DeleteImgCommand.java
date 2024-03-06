package com.freediving.authservice.application.port.in;

import java.util.List;

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
public class DeleteImgCommand extends SelfValidating<DeleteImgCommand> {

	@NotNull(message = "이미지 URL 리스트 정보는 필수 값입니다.")
	private List<String> imgUrlList;

	public DeleteImgCommand(List<String> imgUrlList) {
		this.imgUrlList = imgUrlList;
		this.validateSelf();
	}
}
