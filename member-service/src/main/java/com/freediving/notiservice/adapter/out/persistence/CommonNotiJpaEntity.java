package com.freediving.notiservice.adapter.out.persistence;

import com.freediving.common.persistence.AuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "common_noti")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommonNotiJpaEntity extends AuditableEntity {

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

	@Column(name = "source_user_id", nullable = false)
	private Long sourceUserId;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "link_code")
	private String linkCode;

	@Column(name = "link_data")
	private String linkData;
}
