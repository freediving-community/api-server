package com.freediving.memberservice.application.port.out;

import com.freediving.memberservice.application.port.in.CreateUserInfoCommandV2;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/20
 * @Description    : 유저 생성 / 업데이트 작업을 전달하기 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/20        sasca37       최초 생성
 */

public interface CreateUserPortV2 {

	void createUserInfoV2(CreateUserInfoCommandV2 command);

}
