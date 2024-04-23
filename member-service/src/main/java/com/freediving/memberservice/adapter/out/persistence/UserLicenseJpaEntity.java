package com.freediving.memberservice.adapter.out.persistence;

import com.freediving.common.domain.member.RoleLevel;
import com.freediving.common.persistence.AuditableEntity;
import com.freediving.memberservice.domain.DiveType;
import com.freediving.memberservice.domain.LicenseStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : 유저 라이센스 정보를 저장하는 JPA Entity (관리자 승인이 존재하기 때문에 별도의 엔티티로 관리)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */
@Entity
@Table(name = "user_license")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserLicenseJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "license_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserJpaEntity userJpaEntity;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role", nullable = false, length = 50)
	private RoleLevel role;

	@Column(name = "org_name")
	private String orgName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "dive_type", nullable = false)
	private DiveType diveType;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "license_status", nullable = false, length = 20)
	private LicenseStatus licenseStatus;

	@Column(name = "license_level")
	private Integer licenseLevel;

	@Column(name = "license_img_url")
	private String licenseImgUrl;

	@Column(name = "confirm_tf", nullable = false, length = 10)
	private Boolean confirmTF;

	@Column(name = "confirm_admin_id")
	private Long confirmAdminId;

	@PrePersist
	void prePersist() {
		this.confirmTF = false;
		this.licenseStatus = LicenseStatus.EVALUATION;
	}

	public static UserLicenseJpaEntity createUserLicenseJpaEntity(UserJpaEntity userJpaEntity, DiveType diveType) {
		return new UserLicenseJpaEntity(userJpaEntity, diveType, RoleLevel.UNREGISTER);
	}

	private UserLicenseJpaEntity(UserJpaEntity userJpaEntity, DiveType diveType, RoleLevel roleLevel) {
		this.userJpaEntity = userJpaEntity;
		this.diveType = diveType;
		this.role = roleLevel;
	}

	public void updateRoleLevel(RoleLevel roleLevel) {
		this.role = roleLevel;
	}

	public void updateLicenseImgUrl(String licenseImgUrl) {
		this.licenseImgUrl = licenseImgUrl;
	}

	public void updateLicenseLevel(Integer licenseLevel) {
		this.licenseLevel = licenseLevel;
	}
}
