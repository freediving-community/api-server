package com.freediving.communityservice.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

// @RefreshScope
@RequiredArgsConstructor
@Component
public class CommunityMessage {
	private final MessageSource message;
	private static final String PREFIX_COMMUNITY = "community.";

	public String getMessage(String key) {
		return message.getMessage(PREFIX_COMMUNITY + key, null, Locale.getDefault());
	}

	public String getMessage(String key, String argument) {
		return message.getMessage(PREFIX_COMMUNITY + key, new Object[] {argument}, Locale.getDefault());
	}

}


