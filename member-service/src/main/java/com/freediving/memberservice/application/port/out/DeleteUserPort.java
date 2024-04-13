package com.freediving.memberservice.application.port.out;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/31
 * @Description    : 유저 삭제 작업을 전달하기 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/31        sasca37       최초 생성
 */

public interface DeleteUserPort {

	void deleteUser(Long userId);
}
