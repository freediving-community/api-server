package com.freediving.buddyservice.adapter.out.persistence.preference;

import com.freediving.common.enumerate.DivingPool;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_diving_pool")
@IdClass(UserDivingPoolId.class)
public class UserDivingPoolEntity {

	@Id
	private Long userId;

	@Id
	@Column(name = "diving_pool_id")
	@Enumerated(EnumType.STRING)
	private DivingPool divingPoolId;
}