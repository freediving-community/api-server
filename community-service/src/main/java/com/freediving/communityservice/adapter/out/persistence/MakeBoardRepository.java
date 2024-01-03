package com.freediving.communityservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MakeBoardRepository extends JpaRepository<BoardJpaEntity, Long> {
}
