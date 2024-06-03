package com.freediving.divingpool.data.dao.persistence;

import java.io.Serializable;
import java.util.Objects;

import com.freediving.common.enumerate.DivingPool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDivingPoolId implements Serializable {
	private Long userId;
	private DivingPool divingPoolId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDivingPoolId that = (UserDivingPoolId)o;
		return Objects.equals(userId, that.userId) &&
			Objects.equals(divingPoolId, that.divingPoolId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, divingPoolId);
	}
}