package com.freediving.authservice.application.port.out;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/03
 * @Description    : 이미지 관리를 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */
public interface CreateImgPort {

	void saveImg(String preSignedUrl, String cdnUrl);
}
