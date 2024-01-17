package com.freediving.memberservice.adapter.out.persistence;

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
 * @Description    : 가입한 유저의 토큰 정보를 저장하는 Entity ( 자주 업데이트 되기 때문에 별도의 Entity로 관리 )
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */

@Entity
@Table(name = "user_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserTokenJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_token_id")
	private Long id;

	@Column(name = "refresh_token", nullable = false)
	private String refreshToken;

	@Column(name = "fcm_token")
	private String fcmToken;

	public static UserTokenJpaEntity createToken(String refreshToken) {
		return new UserTokenJpaEntity(refreshToken);
	}

	private UserTokenJpaEntity(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
