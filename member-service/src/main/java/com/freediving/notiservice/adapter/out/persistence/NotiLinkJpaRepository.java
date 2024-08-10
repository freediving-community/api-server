package com.freediving.notiservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : sasca37
 * @Date : 2024/07/21
 * @Description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/21        sasca37       최초 생성
 */
@Repository
public interface NotiLinkJpaRepository extends JpaRepository<NotiLinkJpaEntity, String> {

}
