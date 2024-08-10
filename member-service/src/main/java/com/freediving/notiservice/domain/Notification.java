package com.freediving.notiservice.domain;

import java.time.LocalDateTime;

import com.freediving.notiservice.application.port.in.CreateNotiCommand;

import lombok.Builder;

@Builder
public record Notification(Long targetUserId, String screenName,
						   String type, Long sourceUserId, String sourceUserProfileImgUrl,
						   String title, String content,
						   String linkCode, String linkData,
						   LocalDateTime createdAt
) {

	public static Notification fromCommand(CreateNotiCommand command, String profileImgUrl) {
		return Notification.builder()
			.targetUserId(command.getTargetUserId())
			.screenName(command.getScreenName())
			.type(command.getType())
			.sourceUserId(command.getSourceUserId())
			.sourceUserProfileImgUrl(profileImgUrl)
			.title(command.getTitle())
			.content(command.getContent())
			.linkCode(command.getLinkCode())
			.linkData(command.getLinkData())
			.createdAt(command.getCreatedAt())
			.build();
	}
}
