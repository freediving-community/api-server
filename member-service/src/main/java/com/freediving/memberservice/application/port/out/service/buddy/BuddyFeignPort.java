package com.freediving.memberservice.application.port.out.service.buddy;

import org.springframework.http.ResponseEntity;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.memberservice.adapter.out.dto.UserConceptResponse;
import com.freediving.memberservice.adapter.out.dto.UserPoolResponse;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/10
 * @Description    : BuddyService와 Feign 통신을 하는 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/10        sasca37       최초 생성
 */
public interface BuddyFeignPort {

	ResponseEntity<ResponseJsonObject<UserPoolResponse>> userDivingPoolListByUserId(Long userId);

	ResponseEntity<ResponseJsonObject<UserConceptResponse>> userConceptListByUserId(Long userId);
}
