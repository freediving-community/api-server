package com.freediving.authservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.authservice.application.port.in.DeleteImgCommand;
import com.freediving.authservice.application.port.in.DeleteImgUseCase;
import com.freediving.authservice.application.port.out.DeleteImgPort;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DeleteImgService implements DeleteImgUseCase {

	private final DeleteImgPort deleteImgPort;

	@Override
	public void deleteImgs(DeleteImgCommand command) {
		deleteImgPort.deleteImgList(command.getImgUrlList());
	}
}
