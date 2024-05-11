package com.freediving.communityservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;

@Configuration
@EnableFeignClients(basePackages = "com.freediving.communityservice")
public class CommunityFeignConfig {
	@Bean
	public Client feignClient() {
		return new Client.Default(null, null);
	}
}
