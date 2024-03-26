package com.freediving.memberservice.application.port.out.service.buddy;

import org.springframework.http.ResponseEntity;

import com.freediving.common.config.annotation.UseCase;
import com.freediving.common.response.ResponseJsonObject;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 인증을 완료한 사용자 정보를 MemberService에 전달하기 위한 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */
@UseCase
public interface BuddyUseCase {
	ResponseEntity<ResponseJsonObject<?>> getDivingPools();
}
