package com.freediving.notiservice.util;

import com.freediving.common.domain.noti.NotiCode;
import com.freediving.notiservice.adapter.out.persistence.NotiLinkJpaEntity;
import com.freediving.notiservice.adapter.out.persistence.NotiLinkJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author : sasca37
 * @Date : 2024/07/23
 * @Description : 알림 링크 공통 코드 초기화
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/23        sasca37       최초 생성
 */

@Component
@AllArgsConstructor
public class NotiInitCreator implements InitializingBean {

    @Autowired
    private final NotiLinkJpaRepository notLinkJpaRepository;

    @Override
    public void afterPropertiesSet() {
        // 서버 기동 시 중복 데이터 입수 예외 발생 안되도록 예외 무시
        try {
            Arrays.stream(NotiCode.values()).map(NotiLinkJpaEntity::fromNotiCode).forEach(e -> notLinkJpaRepository.save(e));
        } catch (Exception e) { // Exception Ignore
        }
    }
}
