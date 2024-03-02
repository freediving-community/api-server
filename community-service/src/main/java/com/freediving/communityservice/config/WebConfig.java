package com.freediving.communityservice.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.freediving.communityservice.adapter.in.web.UserProviderArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final UserProviderArgumentResolver userProviderArgumentResolver;

	@Autowired
	public WebConfig(UserProviderArgumentResolver userProviderArgumentResolver) {
		this.userProviderArgumentResolver = userProviderArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userProviderArgumentResolver);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToEnumConverter());
	}
}

