package com.freediving.memberservice.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freediving.memberservice.domain.DiveType;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/28
 * @Description    : UserLicenseJpaEntity를 저장하기 위한 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/28        sasca37       최초 생성
 */
@Repository
public interface UserLicenseJpaRepository extends JpaRepository<UserLicenseJpaEntity, Long> {

	@Query(
		"SELECT u " +
			"FROM UserLicenseJpaEntity u " +
			"WHERE u.userJpaEntity = :userJpaEntity"
	)
	List<UserLicenseJpaEntity> findAllByUserJpaEntity(@Param("userJpaEntity") UserJpaEntity userJpaEntity);

	@Query(
		"SELECT u " +
			"FROM UserLicenseJpaEntity u " +
			"JOIN FETCH  UserJpaEntity  uj " +
			"ON u.userJpaEntity.userId = uj.userId " +
			"WHERE u.userJpaEntity.userId in :userIdList " +
			"ORDER BY uj.userId"
	)
	List<UserLicenseJpaEntity> findAllByUserIdList(
		@Param("userIdList") List<Long> userIdList);

	@Query(
		"SELECT u " +
			"FROM UserLicenseJpaEntity u " +
			"JOIN FETCH  UserJpaEntity  uj " +
			"ON u.userJpaEntity.userId = uj.userId " +
			"WHERE u.userJpaEntity.userId = :userId " +
			"AND u.diveType = :diveType"
	)
	UserLicenseJpaEntity findByUserIdAndDiveType(@Param("userId") Long userId, @Param("diveType") DiveType diveType);
}
