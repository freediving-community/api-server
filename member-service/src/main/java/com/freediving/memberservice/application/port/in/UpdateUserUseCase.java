package com.freediving.memberservice.application.port.in;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/26
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/26        sasca37       최초 생성
 */
@UseCase
public interface UpdateUserUseCase {
	FindUserResponse updateUserInfo(UpdateUserInfoCommand command);
}
