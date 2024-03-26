package com.freediving.memberservice.adapter.in.web.dto;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/06
 * @Description    : 닉네임 중복 확인 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/06        sasca37       최초 생성
 */
public record FindNicknameResponse(
	boolean isAvailableTF
) {
}

