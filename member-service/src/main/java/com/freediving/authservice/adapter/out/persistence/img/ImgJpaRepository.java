package com.freediving.authservice.adapter.out.persistence.img;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/03
 * @Description    : Img 등록 정보를 저장하는 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */
@Repository
public interface ImgJpaRepository extends JpaRepository<ImgJpaEntity, Long> {

}
