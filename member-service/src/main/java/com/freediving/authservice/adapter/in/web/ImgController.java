package com.freediving.authservice.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.adapter.in.web.dto.CreateImgRequest;
import com.freediving.authservice.application.port.in.CreateImgCommand;
import com.freediving.authservice.application.port.in.ImgUseCase;
import com.freediving.common.config.annotation.WebAdapter;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : 이미지 Presigned URL 정보를 생성하는 컨트롤러
 * 					( AWS S3를 사용하지만, 추후 CloudFlare 전환 가능성 존재)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/img")
@Slf4j
@Tag(name = "Image", description = "이미지 Presigned URL 정보를 생성")
public class ImgController {

	private final ImgUseCase imgUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/21
	 * @Param            : PreSigned URL 생성을 위한 Request DTO
	 * @Return           : PreSigned URL
	 * @Description      : 이미지 정보를 전달받아 PreSigned URL 정보 반환
	 */
	@PostMapping
	public String createPreSignedUrl(@RequestBody CreateImgRequest createImgRequest) {
		CreateImgCommand createImgCommand = CreateImgCommand.builder()
			.directory(createImgRequest.directory())
			.build();
		return imgUseCase.createPreSignedUrl(createImgCommand);
	}
}
