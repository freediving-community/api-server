package com.freediving.memberservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freediving.memberservice.domain.OauthType;

@Repository
public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {

	UserJpaEntity findByOauthTypeAndEmail(OauthType oauthType, String email);
}
