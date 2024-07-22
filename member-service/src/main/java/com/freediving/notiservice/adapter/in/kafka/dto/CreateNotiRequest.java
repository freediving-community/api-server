package com.freediving.notiservice.adapter.in.kafka.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ToString
public class CreateNotiRequest {
	private Long targetUserId;
	private String screenName;
	private String type;
	private Long sourceUserId;
	private String title;
	private String content;
	private String linkCode;
	private String linkData;
	private LocalDateTime createdAt;

}
