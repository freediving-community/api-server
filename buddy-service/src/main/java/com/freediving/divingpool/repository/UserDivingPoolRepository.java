package com.freediving.divingpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freediving.divingpool.data.dao.persistence.UserDivingPoolEntity;
import com.freediving.divingpool.data.dao.persistence.UserDivingPoolId;

public interface UserDivingPoolRepository extends JpaRepository<UserDivingPoolEntity, UserDivingPoolId> {
	void deleteByUserId(Long userId);

	List<UserDivingPoolEntity> findAllByUserId(Long userId);
}