package com.freediving.buddyservice.divingpool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.divingpool.data.dao.DivingPoolJpaEntity;

@Repository
public interface DivingPoolRepository extends JpaRepository<DivingPoolJpaEntity, String> {

	List<DivingPoolJpaEntity> findAllByIsVisibleTrue();

}
