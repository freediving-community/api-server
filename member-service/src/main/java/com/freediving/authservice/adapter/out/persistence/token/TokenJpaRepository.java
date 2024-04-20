package com.freediving.authservice.adapter.out.persistence.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/15
 * @Description    : Token 정보를 저장하는 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/15        sasca37       최초 생성
 * 2024/01/25 		 sasca37       Token 관리 주체를 MemberService에서 AuthService로 변경
 */
@Repository
public interface TokenJpaRepository extends JpaRepository<TokenJpaEntity, Long> {

	Optional<TokenJpaEntity> findByUserId(String userId);
}
