package com.freediving.divingpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freediving.common.enumerate.DivingPool;
import com.freediving.divingpool.data.dao.DivingPoolJpaEntity;

@Repository
public interface DivingPoolRepository extends JpaRepository<DivingPoolJpaEntity, DivingPool> {

	List<DivingPoolJpaEntity> findAllByIsVisibleTrue();

}
