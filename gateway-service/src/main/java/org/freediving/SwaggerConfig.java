package org.freediving;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${swagger.servers.url}")
	private String url = "http://localhost";
	@Value("${swagger.servers.description}")
	private String description = "Server Description";
	@Value("${swagger.servers.title}")
	private String title = "BuddyMe Service Swagger";
	@Value("${swagger.servers.version}")
	private String version = "V1";

	@Bean
	public OpenAPI customOpenAPI() {

		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.name("Authorization");

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList("bearerAuth");

		return new OpenAPI().addServersItem(new Server().url(url).description(description))
			.components(new Components().addSecuritySchemes("Access Token", securityScheme))
			.security(List.of(securityRequirement))
			.info(new Info().title(title).version(version));
	}
}
