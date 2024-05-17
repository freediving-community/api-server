package com.freediving.buddyservice.config;

import java.util.Arrays;
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

		// 기본 응답 추가
		Components components = new Components()
			.addSecuritySchemes("Access Token", createSecurityScheme())
			.addSecuritySchemes("User-Id", createUserIdHeaderScheme());
		addBuddyServiceResponses(components);

		return new OpenAPI()
			.addServersItem(new Server().url(url).description(description))
			.components(components)
			.security(List.of(createSecurityRequirement()))
			.info(new Info().title(title).version(version));

	}

	private SecurityRequirement createSecurityRequirement() {
		// SecurityRequirement 설정
		return new SecurityRequirement().addList("Access Token").addList("User-Id");
	}

	private SecurityScheme createSecurityScheme() {
		// SecurityScheme 설정
		return new SecurityScheme()
			.name("Access Token")
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");
	}

	private SecurityScheme createUserIdHeaderScheme() {
		// User-Id 헤더 설정
		return new SecurityScheme()
			.name("User-Id")
			.type(SecurityScheme.Type.APIKEY)
			.in(SecurityScheme.In.HEADER)
			.name("User-Id");
	}

	private void addBuddyServiceResponses(Components components) {
		// 3000 이상 코드의 ServiceStatusCode 응답 추가
		Arrays.stream(ServiceStatusCode.values())
			.filter(code -> (code.getCode() >= 3000 || code.getCode() < 1000))
			.forEach(code -> components.addResponses(String.valueOf(code.getCode()), createApiResponse(code)));
	}

	private ApiResponse createApiResponse(ServiceStatusCode statusCode) {
		// ApiResponse 생성
		return new ApiResponse()
			.description(statusCode.getMessage())
			.content(new Content()
				.addMediaType("application/json", new MediaType()
					.example(ResponseJsonObject.builder()
						.code(statusCode)
						.expandMsg("Example message")
						.build())));
	}

}
