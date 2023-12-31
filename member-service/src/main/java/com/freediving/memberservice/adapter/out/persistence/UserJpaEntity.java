package com.freediving.memberservice.adapter.out.persistence;

import com.freediving.common.persistence.AuditableEntity;
import com.freediving.memberservice.domain.OauthType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Enumerated(value = EnumType.STRING)
	private OauthType oauthType;
	private String email;
	private String profileImgUrl;

	public UserJpaEntity(OauthType oauthType, String email, String profileImgUrl) {
		this.email = email;
		this.profileImgUrl = profileImgUrl;
		this.oauthType = oauthType;
	}
}
