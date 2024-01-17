package com.freediving.memberservice.domain;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : 유저가 등록한 라이센스 정보를 저장하는 불변 도메인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */
public record UserLicence(Long userId, String licenceImgUrl, Boolean confirmTF, Long confirmAdminId) {

	public static UserLicence createUserLicence(Long userId, String licenceImgUrl, Boolean confirmTF,
		Long confirmAdminId) {
		return new UserLicence(userId, licenceImgUrl, confirmTF, confirmAdminId);
	}
}
