// package com.freediving.communityservice.adapter.out.persistence.hashtag;
//
// import java.time.LocalDateTime;
//
// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.format.annotation.DateTimeFormat;
//
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Index;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
//
// @Getter
// @NoArgsConstructor
// @AllArgsConstructor
// @Table(name = "hashtag", indexes = {
// 	@Index(columnList = "name")
// })
// @Entity
// public class HashtagJpaEntity {
//
// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	private Long hashtagId;
//
// 	@Setter
// 	@Column(unique = true, nullable = false, length = 50)
// 	private String name;
//
// 	@Setter
// 	@Column(nullable = false)
// 	private Long usedCount;
//
// 	// Auditing
// 	@CreatedDate
// 	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
// 	@Column(nullable = false, updatable = false)
// 	private LocalDateTime createdAt;
//
// 	private HashtagJpaEntity(Long hashtagId, String name, Long usedCount) {
// 		this.hashtagId = hashtagId;
// 		this.name = name;
// 		this.usedCount = usedCount;
// 	}
//
// 	public static HashtagJpaEntity of(Long hashtagId, String name, Long usedCount) {
// 		return new HashtagJpaEntity(hashtagId, name, usedCount);
// 	}
// }
