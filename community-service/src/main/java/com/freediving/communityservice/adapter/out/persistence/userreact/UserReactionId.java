package com.freediving.communityservice.adapter.out.persistence.userreact;

import java.io.Serializable;

import org.springframework.data.annotation.CreatedBy;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;
import com.freediving.communityservice.adapter.out.persistence.constant.UserReactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserReactionId implements Serializable {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BoardType boardType;

	@Column(nullable = false)
	private Long articleId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserReactionType userReactionType;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private Long createdBy;

}
