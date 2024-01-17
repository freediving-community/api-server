package com.freediving.memberservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : UserToken 정보를 저장하는 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 */
@Repository
public interface UserTokenJpaRepository extends JpaRepository<UserTokenJpaEntity, Long> {

}
