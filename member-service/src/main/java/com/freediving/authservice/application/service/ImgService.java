package com.freediving.authservice.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.freediving.authservice.adapter.in.web.dto.CreateImgResponse;
import com.freediving.authservice.application.port.in.CreateImgCommand;
import com.freediving.authservice.application.port.in.CreateImgUseCase;
import com.freediving.authservice.application.port.in.ImgUseCase;
import com.freediving.authservice.application.port.out.CreateImgPort;
import com.freediving.authservice.application.port.out.ImgPort;
import com.freediving.authservice.util.ImgUtils;
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
public class ImgService implements ImgUseCase, CreateImgUseCase {

	private final ImgPort imgPort;

	private final CreateImgPort createImgPort;

	@Override
	public CreateImgResponse createPreSignedUrl(CreateImgCommand createImgCommand) {
		Long userId = createImgCommand.getUserId();
		String uniqueFileName = ImgUtils.createUniqueFileName(userId);
		String ext = createImgCommand.getExt();
		String imgPath = ImgUtils.createPath(createImgCommand.getDirectory(), uniqueFileName, ext);
		CreateImgResponse response = imgPort.generatePreSignedUrl(imgPath);
		saveImg(ImgUtils.parsingPreSignedUrl(response.preSignedUrl()), response.cloudFrontUrl());
		return response;
	}

	@Override
	public void saveImg(String parsedPreSignedUrl, String cdnUrl) {
		createImgPort.saveImg(parsedPreSignedUrl, cdnUrl);
	}
}
