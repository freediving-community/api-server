package com.freediving.authservice.util;

import java.util.Date;
import java.util.UUID;

import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

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

	public static String createPath(String directory, String uniqueImgName, String ext) {
		return String.format("images/%s/%s.%s", directory, uniqueImgName, ext);
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

	public static String convertToCdnUrl(String cdnImgPath, String preSignedUrl) {

		int startIdx = preSignedUrl.indexOf(".com");
		int endIdx = preSignedUrl.indexOf("?");

		if (startIdx == -1 || endIdx == -1) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "PreSigned URL 작업 중 서버 오류가 발생했습니다.");
		}
		return cdnImgPath + preSignedUrl.substring(startIdx + 4, endIdx);
	}

	public static String parsingPreSignedUrl(String preSignedUrl) {
		int endIdx = preSignedUrl.indexOf("?");
		if (endIdx == -1) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "PreSigned URL 파싱 중 서버 오류가 발생했습니다.");
		}
		return preSignedUrl.substring(0, endIdx);
	}

	public static String parsingKeyImgUrl(String imgUrl) {
		int startIdx = imgUrl.indexOf("/images");
		if (startIdx == -1) {
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "이미지 키 파싱 중 서버 오류가 발생했습니다.");
		}
		return imgUrl.substring(startIdx + 1);
	}
}
