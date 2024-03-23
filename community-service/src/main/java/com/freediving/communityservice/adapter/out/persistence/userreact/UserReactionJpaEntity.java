package com.freediving.communityservice.adapter.out.persistence.userreact;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_reaction")
@Entity
public class UserReactionJpaEntity implements Persistable<UserReactionId> {

	@EmbeddedId
	private UserReactionId userReactionId;

	@CreatedDate
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public static UserReactionJpaEntity of(UserReactionType userReactionType, BoardType boardType, Long articleId,
		Long userId) {
		UserReactionId id = UserReactionId.builder()
			.boardType(boardType)
			.articleId(articleId)
			.userReactionType(userReactionType)
			.createdBy(userId)
			.build();

		return new UserReactionJpaEntity(id, null);
	}

	@Override
	public UserReactionId getId() {
		return this.userReactionId;
	}

	@Override
	public boolean isNew() {
		return this.createdAt == null;
	}
}
