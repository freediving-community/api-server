package com.freediving.buddyservice.config.enumerate;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UserStatusDeserializer extends JsonDeserializer<UserStatus> {
	@Override
	public UserStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String value = p.getText();
		return UserStatus.valueOf(value.toUpperCase());
	}
}