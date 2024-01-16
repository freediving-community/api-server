package com.freediving.buddyservice.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerServersConfig {

	@Value("${swagger.servers.url}")
	private String url;
	@Value("${swagger.servers.description}")
	private String description;
	@Value("${swagger.servers.title}")
	private String title;
	@Value("${swagger.servers.version}")
	private String version;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().addServersItem(new Server().url(url).description(description))
			.components(new Components().addHeaders("Authorization",
				new Header()))
			.info(new Info().title(title).version(version));
	}
}
