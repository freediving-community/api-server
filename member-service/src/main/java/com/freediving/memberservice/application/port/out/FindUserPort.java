package com.freediving.memberservice.application.port.out;

import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 유저 정보를 기반으로 조회하기 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

public interface FindUserPort {

	User findUser(Long userId);
}
