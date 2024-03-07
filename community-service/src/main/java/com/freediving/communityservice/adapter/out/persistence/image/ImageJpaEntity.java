package com.freediving.communityservice.adapter.out.persistence.image;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
@Entity
public class ImageJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(nullable = false, unique = true)
	private String imageServerId;

	@Column(nullable = false, length = 1000)
	private String url;

	private Integer width;

	private Integer height;

	private String style;

	private String description;

	private String quality;

	// @Column(nullable = false)
	// private String serviceName;

	private String domainName;

	private String domainId;

	private String originName;

	@Column(nullable = false)
	private String extension;

	@Column(nullable = false)
	private Integer size;

	private boolean secret;

	@Column(nullable = false)
	private Long createdBy;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public ImageJpaEntity(String imageServerId, String url, Integer width, Integer height, String style,
		String description, String quality, String domainName, String domainId, String originName, String extension,
		Integer size, boolean secret, Long createdBy, LocalDateTime createdAt) {
		this.imageServerId = imageServerId;
		this.url = url;
		this.width = width;
		this.height = height;
		this.style = style;
		this.description = description;
		this.quality = quality;
		this.domainName = domainName;
		this.domainId = domainId;
		this.originName = originName;
		this.extension = extension;
		this.size = size;
		this.secret = secret;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}
}
