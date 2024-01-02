package com.freediving.communityservice.adapter.out.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "board", indexes = {
	@Index(columnList = "boardName")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class BoardJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private BoardType boardType;

	@Column(nullable = false, length = 50)
	private String boardName;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, length = 10)
	private int sortOrder;

	// Auditing
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@Column(nullable = false)
	@CreatedBy
	private Long createdBy;

	@Column(nullable = false)
	@LastModifiedBy
	private Long modifiedBy;


/* 특정 게시판의 기본 정렬 타입
	@ColumnDefault("")
	@Column
	@Enumerated(EnumType.STRING)
	private ListOrderType listOrderType;
*/

}
