package com.freediving.memberservice.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freediving.memberservice.domain.OauthType;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : UserJpaEntity를 저장하기 위한 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

	@Query(
		"SELECT u " +
			"FROM UserJpaEntity u " +
			"WHERE u.oauthType = :oauthType " +
			"AND u.providerId = :providerId"
	)
	Optional<UserJpaEntity> findByOauthTypeAndProviderId(@Param("oauthType") OauthType oauthType,
		@Param("providerId") String providerId);

	@Query(
		"SELECT u " +
			"FROM UserJpaEntity u " +
			"WHERE u.userId = :userId"
	)
	Optional<UserJpaEntity> findUserDetailById(@Param("userId") Long userId);

	Optional<UserJpaEntity> findByNickname(String trimSafeNickname);
}
