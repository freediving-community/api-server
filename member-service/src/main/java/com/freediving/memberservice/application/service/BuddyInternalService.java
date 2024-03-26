package com.freediving.memberservice.application.service;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.memberservice.application.port.out.service.buddy.BuddyFeignPort;
import com.freediving.memberservice.application.port.out.service.buddy.BuddyUseCase;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/14
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/14        sasca37       최초 생성
 */
@UseCase
@RequiredArgsConstructor
@Transactional
public class BuddyInternalService implements BuddyUseCase {

	private final BuddyFeignPort buddyFeignPort;

	@Override
	public ResponseEntity<ResponseJsonObject<?>> getDivingPools() {

		return buddyFeignPort.getDivingPools();
	}
}
