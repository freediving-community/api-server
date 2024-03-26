package com.freediving.memberservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
