package com.freediving.memberservice.adapter.out.persistence;

import com.freediving.common.persistence.AuditableEntity;
import com.freediving.memberservice.domain.DiveType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "user_licence")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserLicenceJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "licence_id")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "dive_type", nullable = false)
	private DiveType diveType;

	@Column(name = "licence_level", nullable = false)
	private Integer licenceLevel;

	@Column(name = "licence_img_url", nullable = false)
	private String licenceImgUrl;

	@Column(name = "confirm_tf", nullable = false, length = 10)
	private Boolean confirmTF;

	@Column(name = "confirm_admin_id")
	private Long confirmAdminId;

	@PrePersist
	void prePersist() {
		this.confirmTF = false;
	}

	public void updateLicenceImgUrl(String licenceImgUrl) {
		this.licenceImgUrl = licenceImgUrl;
	}

	public static UserLicenceJpaEntity createUserLicenceJpaEntity(DiveType diveType, Integer licenceLevel,
		String licenceImgUrl) {
		return new UserLicenceJpaEntity(diveType, licenceLevel, licenceImgUrl);
	}

	private UserLicenceJpaEntity(DiveType diveType, Integer licenceLevel, String licenceImgUrl) {
		this.diveType = diveType;
		this.licenceLevel = licenceLevel;
		this.licenceImgUrl = licenceImgUrl;
	}
}
