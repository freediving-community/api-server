package com.freediving.memberservice.application.port.in;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/31
 * @Description    : 유저 삭제 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/31        sasca37       최초 생성
 */

public interface DeleteUserUseCase {

	void deleteUser(Long userId);
}
