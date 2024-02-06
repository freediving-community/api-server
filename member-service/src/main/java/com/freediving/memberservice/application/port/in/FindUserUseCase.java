package com.freediving.memberservice.application.port.in;

import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : Query 객체로 유저 정보를 조회하기 위한 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

public interface FindUserUseCase {

	User findUserDetailByQuery(FindUserQuery findUserQuery);
}
