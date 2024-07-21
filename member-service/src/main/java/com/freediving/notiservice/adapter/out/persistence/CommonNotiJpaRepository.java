package com.freediving.notiservice.adapter.out.persistence;

import com.freediving.memberservice.adapter.out.persistence.UserJpaEntity;
import com.freediving.memberservice.domain.OauthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author         : sasca37
 * @Date           : 2024/07/21
 * @Description    : 공토 알림 이력 정보를 저장하기 위한 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/21        sasca37       최초 생성
 */
@Repository
public interface CommonNotiJpaRepository extends JpaRepository<CommonNotiJpaEntity, Long> {

}
