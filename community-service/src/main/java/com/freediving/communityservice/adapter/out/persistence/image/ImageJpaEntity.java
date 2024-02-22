package com.freediving.communityservice.adapter.out.persistence.image;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "image", indexes = {

})

@Entity
public class ImageJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String imageServerId;

	@Column(nullable = false, length = 500)
	private String url;

	private Integer width;

	private Integer height;

	private String style;

	private String description;

	private String quality;

	@Column(nullable = false)
	private String serviceName;

	@Column(nullable = false)
	private String domainName;

	@Column(nullable = false)
	private String domainId;

	@Column(nullable = false)
	private String originName;

	@Column(nullable = false)
	private String extension;

	@Column(nullable = false)
	private Integer size;

	@Column(nullable = false)
	private boolean secret;

	@Column(nullable = false)
	private Long createdBy;

	@Column(nullable = false)
	private LocalDateTime createdAt;

}
