package com.freediving.communityservice.adapter.out.persistence.userreact;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_reaction")
@Entity
public class UserReactionJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private UserReactionType userReactionType;

	@Enumerated(EnumType.STRING)
	private BoardType boardType;

	private Long articleId;

	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private Long createdBy;

	public static UserReactionJpaEntity of(UserReactionType userReactionType, BoardType boardType, Long articleId) {
		return new UserReactionJpaEntity(null, userReactionType, boardType, articleId, null, null);
	}

}
