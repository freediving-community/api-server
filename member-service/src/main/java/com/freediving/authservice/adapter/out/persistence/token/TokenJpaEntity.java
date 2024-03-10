package com.freediving.authservice.adapter.out.persistence.token;

import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : 가입한 유저의 토큰 정보를 저장하는 Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 * 2024/01/25 		 sasca37       Token 관리 주체를 MemberService에서 AuthService로 변경
 */

@Entity
@Table(name = "token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Long id;

	@Column(name = "refresh_token", nullable = false)
	private String refreshToken;

	@Column(name = "fcm_token")
	private String fcmToken;

	public static TokenJpaEntity createToken(String refreshToken) {
		return new TokenJpaEntity(refreshToken);
	}

	private TokenJpaEntity(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
