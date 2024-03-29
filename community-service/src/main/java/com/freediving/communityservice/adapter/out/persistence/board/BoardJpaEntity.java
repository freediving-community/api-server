package com.freediving.communityservice.adapter.out.persistence.board;

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
@Table(name = "board", indexes = {
	@Index(name = "idx_board_boardType", columnList = "boardType")
	//,@Index(name = "idx_board_sortOrder", columnList = "sortOrder")
})
// @EntityListeners(AuditingEntityListener.class)
@Entity
public class BoardJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private BoardType boardType;

	@Column(nullable = false, length = 50, unique = true)
	private String boardName;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, unique = true)
	private int sortOrder;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean enabled;

	// Auditing
	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private Long createdBy;

	@Column(nullable = false)
	@LastModifiedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime modifiedAt;

	@Column(nullable = false)
	@LastModifiedBy
	private Long modifiedBy;

	public BoardJpaEntity(BoardType boardType, String boardName, String description, int sortOrder) {
		this.boardType = boardType;
		this.boardName = boardName;
		this.description = description;
		this.sortOrder = sortOrder;
	}

	public BoardJpaEntity(BoardType boardType, String boardName, String description, int sortOrder, boolean enabled,
		LocalDateTime createdAt, LocalDateTime modifiedAt, Long createdBy, Long modifiedBy) {
		this.boardType = boardType;
		this.boardName = boardName;
		this.description = description;
		this.sortOrder = sortOrder;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	public static BoardJpaEntity of(BoardType boardType, String boardName, String description, int sortOrder) {
		return new BoardJpaEntity(boardType, boardName, description, sortOrder, true, LocalDateTime.now(),
			LocalDateTime.now(), new Random().nextLong(), new Random().nextLong());
	}

}
