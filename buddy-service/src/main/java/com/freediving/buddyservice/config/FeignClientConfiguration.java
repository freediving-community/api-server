package com.freediving.buddyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;

@Configuration
public class FeignClientConfiguration {
	@Bean
	public Decoder feignDecoder() {
		ObjectMapper mapper = new ObjectMapper()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return new JacksonDecoder(mapper);
	}
}