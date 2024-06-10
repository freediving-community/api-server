package com.freediving.memberservice.application.port.in;

import lombok.Builder;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    : 유저 정보 조회를 위한 Query
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */

@Builder
public record FindUserInfoQuery(Long userId) {
}
