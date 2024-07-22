package com.freediving.notiservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.notiservice.application.port.in.CreateNotiCommand;
import com.freediving.notiservice.application.port.in.CreateNotiUseCase;
import com.freediving.notiservice.application.port.out.CreateNotiPort;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/07/22
 * @Description    : 
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/22        sasca37       최초 생성
 */

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateNotiService implements CreateNotiUseCase {

	private final CreateNotiPort createNotiPort;

	@Override
	public void createNoti(CreateNotiCommand command) {
		createNotiPort.createNoti(command);
	}
}
