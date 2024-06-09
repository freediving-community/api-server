package com.freediving.memberservice.application.port.out;

import java.util.List;

import com.freediving.memberservice.adapter.in.web.dto.FindMyPageResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserInfoResponse;
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

	User findUserDetailById(Long userId);

	List<User> findUserListByIds(List<Long> userIds);

	boolean findNickname(String trimSafeNickname);

	FindUserInfoResponse findUserInfoByQuery(Long userId);

	FindMyPageResponse findMyPageByUserId(Long userId);
}
