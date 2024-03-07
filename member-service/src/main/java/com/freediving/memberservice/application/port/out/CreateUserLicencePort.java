package com.freediving.memberservice.application.port.out;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/28
 * @Description    : User Licence 등록 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/28        sasca37       최초 생성
 */
public interface CreateUserLicencePort {

	void createUserLicenceLevel(Long userId, Integer licenceLevel);

	void createUserLicenceImgUrl(Long userId, String licenceImgUrl);
}
