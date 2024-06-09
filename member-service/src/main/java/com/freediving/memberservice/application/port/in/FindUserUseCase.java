package com.freediving.memberservice.application.port.in;

import java.util.List;

import com.freediving.memberservice.adapter.in.web.dto.FindUserInfoResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;
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

	List<FindUserServiceResponse> findUserListByQuery(FindUserListQuery findUserListQuery);

	boolean findNickname(String trimSafeNickname);

	User findUserByUserId(FindUserQuery findUserQuery);

	FindUserInfoResponse findUserInfoByQuery(FindUserInfoQuery query);
}
