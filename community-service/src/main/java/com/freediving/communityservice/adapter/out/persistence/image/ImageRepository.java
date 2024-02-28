package com.freediving.communityservice.adapter.out.persistence.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageJpaEntity, Long> {
}
