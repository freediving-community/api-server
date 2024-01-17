package com.freediving.authservice.adapter.out.external.kakao;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : Kakao 소셜 로그인 후 응답 값 매핑
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31       sasca37       최초 생성
 */

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoInfoResponse(
	Long id,
	boolean hasSignedUp,
	LocalDateTime connectedAt,
	KakaoAccount kakaoAccount
) {

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record KakaoAccount(
		boolean profileNeedsAgreement,
		boolean profileNicknameNeedsAgreement,
		boolean profileImageNeedsAgreement,
		Profile profile,
		boolean nameNeedsAgreement,
		String name,
		boolean emailNeedsAgreement,
		boolean isEmailValid,
		boolean isEmailVerified,
		String email,
		boolean ageRangeNeedsAgreement,
		String ageRange,
		boolean birthyearNeedsAgreement,
		String birthyear,
		boolean birthdayNeedsAgreement,
		String birthday,
		String birthdayType,
		boolean genderNeedsAgreement,
		String gender,
		boolean phoneNumberNeedsAgreement,
		String phoneNumber,
		boolean ciNeedsAgreement,
		String ci,
		LocalDateTime ciAuthenticatedAt
	) {
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record Profile(
		String nickname,
		String thumbnailImageUrl,
		String profileImageUrl,
		boolean isDefaultImage
	) {
	}
}
