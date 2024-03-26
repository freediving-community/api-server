package com.freediving.memberservice.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;

import com.freediving.common.persistence.AuditableEntity;
import com.freediving.memberservice.domain.OauthType;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
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

	@Column(name = "content", length = 400)
	private String content;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "oauth_type", nullable = false, length = 20)
	private OauthType oauthType;

	@Embedded
	private UserPersonalVO userPersonalVO;

	@Convert(converter = OauthTypeSetConverter.class)
	@Column(name = "oauth_interlock")
	private OauthTypeSetVO oauthTypeSetVO;

	@OneToMany(mappedBy = "userJpaEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserLicenseJpaEntity> userLicenseJpaEntityList = new ArrayList<>();

	public void updateUserNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateUserContent(String content) {
		this.content = content;
	}

	public static UserJpaEntity createSimpleUser(OauthType oauthType, String email, String profileImgUrl) {
		return new UserJpaEntity(oauthType, email, profileImgUrl);
	}

	private UserJpaEntity(OauthType oauthType, String email, String profileImgUrl) {
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.oauthType = oauthType;
	}

	@Builder(builderMethodName = "createMockUser")
	public UserJpaEntity(Long userId, String email, String profileImgUrl, OauthType oauthType) {
		this.userId = userId;
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.oauthType = oauthType;
	}
}
