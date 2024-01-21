package com.freediving.authservice.application.port.out;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : 이미지 정보를 전달하기 위한 Port (AWS / CloudFlare)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */
public interface ImgPort {

	String generatePreSignedUrl(String imgPath);
}
