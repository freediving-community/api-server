package com.freediving.buddyservice.adapter.out.persistence.preference;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDivingPoolRepository extends JpaRepository<UserDivingPoolEntity, UserDivingPoolId> {
	void deleteByUserId(Long userId);

	List<UserDivingPoolEntity> findAllByUserId(Long userId);
}