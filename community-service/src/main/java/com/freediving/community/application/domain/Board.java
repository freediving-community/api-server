package com.freediving.community.application.domain;

import com.freediving.community.application.domain.constant.BoardType;
import com.freediving.community.application.domain.constant.ListOrderType;
import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Table(name = "board", indexes = {
	@Index(columnList = "title")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private BoardType boardType;

	@Setter
	@Column(nullable = false, length = 50)
	private String boardName;

	@Setter
	@Column(nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	private ListOrderType listOrderType;

	// Auditing
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@Column(nullable = false)
	@CreatedBy
	private String createdBy;

	@Column(nullable = false)
	@LastModifiedBy
	private String modifiedBy;

	public void setId(Long id) {
		this.id = id;
	}

}
