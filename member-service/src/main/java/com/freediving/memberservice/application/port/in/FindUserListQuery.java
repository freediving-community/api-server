package com.freediving.memberservice.application.port.in;

import java.util.List;

import lombok.Builder;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/27
 * @Description    : 유저 정보 리스트 조회를 위한 Query
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/27        sasca37       최초 생성
 */

@Builder
public record FindUserListQuery(List<Long> userIds, Boolean profileImgTF) {
}
