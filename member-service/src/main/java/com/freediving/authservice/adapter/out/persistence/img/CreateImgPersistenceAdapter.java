package com.freediving.authservice.adapter.out.persistence.img;

import com.freediving.authservice.application.port.out.CreateImgPort;
import com.freediving.common.config.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/03
 * @Description    : 이미지 정보 관리를 위한 Adapter
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateImgPersistenceAdapter implements CreateImgPort {

	private final ImgJpaRepository imgJpaRepository;

	@Override
	public void saveImg(String preSignedUrl, String cdnUrl) {
		ImgJpaEntity imgJpaEntity = ImgJpaEntity.createImgEntity(preSignedUrl, cdnUrl);
		imgJpaRepository.save(imgJpaEntity);
	}
}
