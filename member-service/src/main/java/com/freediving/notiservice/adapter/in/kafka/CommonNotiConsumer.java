package com.freediving.notiservice.adapter.in.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.freediving.notiservice.adapter.out.persistence.CommonNotiJpaRepository;
import com.freediving.notiservice.domain.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommonNotiConsumer {

    private final ObjectMapper objectMapper;
    private final CommonNotiJpaRepository commonNotiJpaRepository;

    @KafkaListener(topics = "${task.topic.common-noti}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeNoti(String message) {
        // TODO : 카프카 메시지 정보 INFC 테이블 적재
        try {
            Notification noti = objectMapper.readValue(message, Notification.class);
            log.info("NOTI INFO : {}", noti);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // TODO : 알림서비스 비즈니스 로직 수행

        // TODO : SSE 구현
    }
}
