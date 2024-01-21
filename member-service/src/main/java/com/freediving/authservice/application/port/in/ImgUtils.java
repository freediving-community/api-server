package com.freediving.authservice.application.port.in;

import java.util.Date;
import java.util.UUID;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : 이미지 정보를 관리하기 위한 Util
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */
public class ImgUtils {

	private static final long EXPIRED_ACCESS_TIME = 2 * 60 * 1000L;

	public static String createPath(String directory, String uniqueImgName) {
		return String.format("%s/%s", directory, uniqueImgName);
	}

	public static Date getImgExpiration() {
		return new Date(System.currentTimeMillis() + EXPIRED_ACCESS_TIME);
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/21
	 * @Param            : 유저 식별 아이디
	 * @Return           : 유저 식별 아이디-UUID로 조합된 파일명
	 * @Description      : 유저 회원 탈퇴 등 일괄 이미지 관리를 위한 유저 아이디-UUID 조합으로 관리
	 */
	public static String createUniqueFileName(Long userId) {
		return String.format("%s-%s", userId, UUID.randomUUID().toString().replace("-", ""));
	}
}
