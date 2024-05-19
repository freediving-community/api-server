package com.freediving.authservice.application.port.out;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/19
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/19        sasca37       최초 생성
 */
public interface FindTokenPort {
	String findRefreshTokenByUserId(Long userId);
}
