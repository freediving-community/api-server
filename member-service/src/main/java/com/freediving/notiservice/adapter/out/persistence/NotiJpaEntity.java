package com.freediving.notiservice.adapter.out.persistence;

import com.freediving.common.persistence.AuditableEntity;
import com.freediving.notiservice.domain.Notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "noti")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class NotiJpaEntity extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "noti_id")
	private Long notiId;

	@Column(name = "target_user_id", nullable = false)
	private Long targetUserId;

	@Column(name = "screen_name", nullable = false)
	private String screenName;

	@Column(name = "type")
	private String type;

	@Column(name = "source_user_id")
	private Long sourceUserId;

	@Column(name = "source_user_profile_img_url")
	private String sourceUserProfileImgUrl;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "link_code")
	private String linkCode;

	@Column(name = "link_data")
	private String linkData;

	public static NotiJpaEntity fromDomain(Notification noti) {
		return NotiJpaEntity.builder()
			.targetUserId(noti.targetUserId())
			.screenName(noti.screenName())
			.type(noti.type())
			.sourceUserId(noti.sourceUserId())
			.sourceUserProfileImgUrl(noti.sourceUserProfileImgUrl())
			.title(noti.title())
			.content(noti.content())
			.linkCode(noti.linkCode())
			.linkData(noti.linkData())
			.build();
	}
}
