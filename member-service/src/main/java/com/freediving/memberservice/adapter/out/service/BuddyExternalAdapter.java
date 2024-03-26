package com.freediving.memberservice.adapter.out.service;

import org.springframework.http.ResponseEntity;

import com.freediving.common.config.annotation.ExternalSystemAdapter;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.memberservice.application.port.out.service.buddy.BuddyFeignPort;
import com.freediving.memberservice.application.port.out.service.buddy.BuddyServiceFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : MemberService 와 통신 작업을 하는 Adapter
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@ExternalSystemAdapter
@RequiredArgsConstructor
@Slf4j
public class BuddyExternalAdapter implements BuddyFeignPort {

	private final BuddyServiceFeignClient buddyServiceFeignClient;

	@Override
	public ResponseEntity<ResponseJsonObject<?>> getDivingPools() {
		return buddyServiceFeignClient.getDivingPools();
	}
}
