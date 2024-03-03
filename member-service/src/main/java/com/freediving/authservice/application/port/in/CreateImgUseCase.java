package com.freediving.authservice.application.port.in;

import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/03
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */

@UseCase
public interface CreateImgUseCase {
	void saveImg(String preSignedUrl, String cdnUrl);
}
