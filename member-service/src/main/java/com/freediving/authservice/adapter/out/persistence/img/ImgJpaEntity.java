package com.freediving.authservice.adapter.out.persistence.img;

import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * @Date           : 2024/03/03
 * @Description    : 이미지 업로드 후 미사용 이미지 관리를 위한 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */

@Entity
@Table(name = "img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImgJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "img_id")
	private Long id;

	@Column(name = "pre_signed_url", nullable = false)
	private String preSignedUrl;

	@Column(name = "cdn_url")
	private String cdnUrl;

	@Column(name = "exist_tf", nullable = false)
	private Boolean existTF;

	@PrePersist
	public void prePersist() {
		this.existTF = false;
	}

	public static ImgJpaEntity createImgEntity(String preSignedUrl, String cdnUrl) {
		return new ImgJpaEntity(preSignedUrl, cdnUrl);
	}

	private ImgJpaEntity(String preSignedUrl, String cdnUrl) {
		this.preSignedUrl = preSignedUrl;
		this.cdnUrl = cdnUrl;
	}
}
