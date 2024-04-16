package com.freediving.buddyservice.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
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

		SecurityScheme userIdHeaderScheme = new SecurityScheme()
			.type(SecurityScheme.Type.APIKEY)
			.in(SecurityScheme.In.HEADER)
			.name("User-Id")
			.description("User-Id long type gateway 없이 버디로 바로 요청시 사용");

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList("Access Token").addList("User-Id");

		return new OpenAPI().addServersItem(new Server().url(url).description(description))
			.components(
				new Components().addSecuritySchemes("Access Token", securityScheme)
					.addSecuritySchemes("User-Id", userIdHeaderScheme)
					.addResponses("400", new ApiResponse().description("Bad Request").content(new Content()
						.addMediaType("application/json", new MediaType()
								.example(
									ResponseJsonObject.builder()
										.code(ServiceStatusCode.BAD_REQUEST)
										.expandMsg("expandMsg")
										.build())
							// 예시 값
						)))
					.addResponses("204", new ApiResponse().description("No Content"))
					.addResponses("401", new ApiResponse().description("Unauthorized"))
					.addResponses("403", new ApiResponse().description("Forbidden"))
					.addResponses("500", new ApiResponse().description("Internal Server Error"))
			)
			.security(List.of(securityRequirement))
			.info(new Info().title(title).version(version));

	}

}
