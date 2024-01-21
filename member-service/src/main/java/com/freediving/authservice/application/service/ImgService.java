package com.freediving.authservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.authservice.application.port.in.CreateImgCommand;
import com.freediving.authservice.application.port.in.ImgUseCase;
import com.freediving.authservice.application.port.in.ImgUtils;
import com.freediving.authservice.application.port.out.ImgPort;
import com.freediving.common.config.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : 이미지 생성 요청에 대한 PreSigned URL 정보를 전달하는 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImgService implements ImgUseCase {

	private final ImgPort imgPort;

	@Override
	public String createPreSignedUrl(CreateImgCommand createImgCommand) {
		// TODO : 유저 식별 아이디 + UUID 조합으로 파일명 생성
		Long userId = 1L;
		String uniqueFileName = ImgUtils.createUniqueFileName(userId);
		String imgPath = ImgUtils.createPath(createImgCommand.getDirectory(), uniqueFileName);
		return imgPort.generatePreSignedUrl(imgPath);
	}
}