package com.freediving.buddyservice.adapter.out.persistence.preference;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDivingPoolRepository extends JpaRepository<UserDivingPoolEntity, UserDivingPoolId> {
	void deleteByUserId(Long userId);
}