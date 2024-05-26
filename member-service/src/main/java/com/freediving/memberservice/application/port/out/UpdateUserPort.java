package com.freediving.memberservice.application.port.out;

import java.util.List;

import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.application.port.in.UpdateUserInfoCommand;
import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/26
 * @Description    : 유저 정보를 기반으로 수정하기 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/26       sasca37       최초 생성
 */

public interface UpdateUserPort {

	FindUserResponse updateUserInfo(UpdateUserInfoCommand command);
}
