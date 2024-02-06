package com.freediving.memberservice.adapter.out.persistence;

import com.freediving.common.persistence.AuditableEntity;
import com.freediving.memberservice.domain.OauthType;
import com.freediving.memberservice.domain.RoleLevel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : 유저 정보를 저장하는 Jpa Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "email", nullable = false, length = 50)
	private String email;

	@Column(name = "profile_img_url")
	private String profileImgUrl;

	@Column(name = "nickname", length = 16)
	private String nickname;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "oauth_type", nullable = false, length = 20)
	private OauthType oauthType;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", length = 20)
	private RoleLevel role;

	@Embedded
	private UserPersonalVO userPersonalVO;

	@Convert(converter = OauthTypeSetConverter.class)
	@Column(name = "oauth_interlock")
	private OauthTypeSetVO oauthTypeSetVO;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "licence_id")
	private UserLicenceJpaEntity userLicenceJpaEntity;

	public void updateUserLicenceJpaEntity(UserLicenceJpaEntity userLicenceJpaEntity) {
		this.userLicenceJpaEntity = userLicenceJpaEntity;
	}

	public static UserJpaEntity createSimpleUser(OauthType oauthType, String email, String profileImgUrl,
		RoleLevel roleLevel) {
		return new UserJpaEntity(oauthType, email, profileImgUrl, roleLevel);
	}

	private UserJpaEntity(OauthType oauthType, String email, String profileImgUrl, RoleLevel role) {
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.oauthType = oauthType;
		this.role = role;
	}

}
