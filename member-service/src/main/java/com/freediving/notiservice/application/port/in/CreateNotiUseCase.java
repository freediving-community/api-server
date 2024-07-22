package com.freediving.notiservice.application.port.in;

import com.freediving.common.config.annotation.UseCase;

/**
 * @Author         : sasca37
 * @Date           : 2024/07/22
 * @Description    : Command 객체를 기반으로 알림 인입을 위한 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/22        sasca37       최초 생성
 */

@UseCase
public interface CreateNotiUseCase {
	void createNoti(CreateNotiCommand command);
}
