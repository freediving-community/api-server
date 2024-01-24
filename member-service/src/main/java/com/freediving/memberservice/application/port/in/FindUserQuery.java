package com.freediving.memberservice.application.port.in;

import lombok.Builder;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 유저 정보 조회를 위한 Query
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@Builder
public record FindUserQuery(Long userId) {
}
