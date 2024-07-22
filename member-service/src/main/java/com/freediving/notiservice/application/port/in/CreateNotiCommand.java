package com.freediving.notiservice.application.port.in;

import java.time.LocalDateTime;

import com.freediving.common.SelfValidating;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/07/22
 * @Description    : 
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/22        sasca37       최초 생성
 */

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateNotiCommand extends SelfValidating<CreateNotiCommand> {
	private Long targetUserId;
	private String screenName;
	private String type;
	private Long sourceUserId;
	private String title;
	private String content;
	private String linkCode;
	private String linkData;
	private LocalDateTime createdAt;

	public CreateNotiCommand(Long targetUserId, String screenName, String type, Long sourceUserId, String title,
		String content, String linkCode, String linkData, LocalDateTime createdAt) {
		this.targetUserId = targetUserId;
		this.screenName = screenName;
		this.type = type;
		this.sourceUserId = sourceUserId;
		this.title = title;
		this.content = content;
		this.linkCode = linkCode;
		this.linkData = linkData;
		this.createdAt = createdAt;
		this.validateSelf();
	}
}
