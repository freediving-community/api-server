package com.freediving.authservice.application.port.in;

import com.freediving.authservice.adapter.in.web.dto.CreateImgResponse;
import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : Image PreSigned URL을 제공하는 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@UseCase
public interface CreateImgUseCase {
	CreateImgResponse createPreSignedUrl(CreateImgCommand createImgCommand);

	void saveImg(String preSignedUrl, String cdnUrl);
}
