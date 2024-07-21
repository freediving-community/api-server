package com.freediving.notiservice.domain;


import java.time.LocalDateTime;

public record Notification(Long targetUserId, String screenName,
						   String type, Long sourceUserId,
						   String title, String content,
						   String linkCode, String linkData,
						   LocalDateTime createdAt
						   ) {
}
