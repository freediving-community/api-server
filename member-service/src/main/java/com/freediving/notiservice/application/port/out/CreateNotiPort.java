package com.freediving.notiservice.application.port.out;

import com.freediving.notiservice.application.port.in.CreateNotiCommand;

/**
 * @Author         : sasca37
 * @Date           : 2024/07/22
 * @Description    : 
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/22        sasca37       최초 생성
 */
public interface CreateNotiPort {

	void createNoti(CreateNotiCommand command);
}
