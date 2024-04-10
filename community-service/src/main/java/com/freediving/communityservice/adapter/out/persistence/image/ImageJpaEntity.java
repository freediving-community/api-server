package com.freediving.communityservice.adapter.out.persistence.image;

import java.time.LocalDateTime;
import java.util.Objects;

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

	@Column(nullable = false)
	private Long articleId;

	@Column(nullable = false, length = 2000)
	private String url;

	private int sortNumber;

	private int size;

	private String extension;

	private LocalDateTime deletedAt;

	@Column(nullable = false)
	private Long createdBy;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	// private int width;

	// private int height;

	// private String style;

	// private String description;

	@Builder
	public ImageJpaEntity(Long articleId, String url, int sortNumber, int size, String extension, Long createdBy,
		LocalDateTime createdAt) {
		this.articleId = articleId;
		this.url = url;
		this.sortNumber = sortNumber;
		this.size = size;
		this.extension = extension;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}

	public void markDeleted(LocalDateTime localDateTime) {
		this.deletedAt = localDateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ImageJpaEntity that))
			return false;
		return Objects.equals(id, that.id) && Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, url);
	}
}
