package com.freediving.notiservice.adapter.out.persistence;

import com.freediving.common.config.annotation.PersistenceAdapter;
import com.freediving.memberservice.adapter.out.persistence.UserJpaEntity;
import com.freediving.memberservice.adapter.out.persistence.UserJpaRepository;
import com.freediving.memberservice.exception.ErrorCode;
import com.freediving.memberservice.exception.MemberServiceException;
import com.freediving.notiservice.application.port.in.CreateNotiCommand;
import com.freediving.notiservice.application.port.out.CreateNotiPort;
import com.freediving.notiservice.domain.Notification;
import lombok.RequiredArgsConstructor;

/**
 * @Author : sasca37
 * @Date : 2024/07/22
 * @Description : 가입 여부를 확인하고 알림 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/22        sasca37       최초 생성
 */

@PersistenceAdapter
@RequiredArgsConstructor
public class CreateNotiPersistenceAdapter implements CreateNotiPort {
    private final UserJpaRepository userJpaRepository;
    private final NotiJpaRepository notiJpaRepository;

    @Override
    public void createNoti(CreateNotiCommand command) {
        // 송신 유저 프로필 이미지 정보 받아오기
        UserJpaEntity userJpaEntity = userJpaRepository.findById(command.getSourceUserId())
                .orElseThrow(() -> new MemberServiceException(ErrorCode.NOT_FOUND_USER));

        Notification noti = Notification.fromCommand(command, userJpaEntity.getProfileImgUrl());
        NotiJpaEntity notiJpaEntity = NotiJpaEntity.fromDomain(noti);
        notiJpaRepository.save(notiJpaEntity);

        // TODO : SSE
    }
}
