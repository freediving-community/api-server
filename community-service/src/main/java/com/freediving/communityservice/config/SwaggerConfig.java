package com.freediving.communityservice.config;

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

	@Value("${swagger.servers.title}")
	private String title;
	@Value("${swagger.servers.version}")
	private String version;
	@Value("${swagger.servers.url}")
	private String url;
	@Value("${swagger.servers.description}")
	private String description;

	private static final String TOKEN_NAME = "JWT_TOKEN";

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.addServersItem(initServer())
			.components(initComponents())
			.security(initSecurityRequirements())
			.info(initInfo());
	}

	private Server initServer() {
		return new Server()
			.url(url)
			.description(description);
	}

	private Components initComponents() {
		return new Components().addSecuritySchemes(TOKEN_NAME, initSecurityScheme());
	}

	private SecurityScheme initSecurityScheme() {
		return new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.name("Authorization");
	}

	private List<SecurityRequirement> initSecurityRequirements() {
		return List.of(new SecurityRequirement().addList(TOKEN_NAME));
	}

	private Info initInfo() {
		return new Info().title(title).version(version).description(description);
	}
}
