package com.freediving.community.application.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Table(name = "hashtag", indexes = {
	@Index(columnList = "name")
})
@Entity
public class HashTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hashtagId;

	@Setter
	@Column(unique = true, nullable = false, length = 50)
	private String name;

	@Setter
	@Column(nullable = false)
	private Long usedCount;

	// Auditing
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	private HashTag(){}

	private HashTag(Long hashtagId, String name, Long usedCount) {
		this.hashtagId = hashtagId;
		this.name = name;
		this.usedCount = usedCount;
	}

	public static HashTag of (Long hashtagId, String name, Long usedCount) {
		return new HashTag(hashtagId, name, usedCount);
	}
}
