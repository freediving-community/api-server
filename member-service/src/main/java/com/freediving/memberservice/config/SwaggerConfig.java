package com.freediving.memberservice.config;

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

/**
 * @Author         : sasca37
 * @Date           : 2024/01/18
 * @Description    : Swagger UI Configuration
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/18        sasca37       최초 생성
 */
@Configuration
public class SwaggerConfig {

	@Value("${swagger.servers.url}")
	private String url;
	@Value("${swagger.servers.description}")
	private String description;
	@Value("${swagger.servers.title}")
	private String title;
	@Value("${swagger.servers.version}")
	private String version;

	private final String CREATED = "201";
	private final String NO_CONTENT = "204";
	private final String BAD_REQUEST = "400";
	private final String UNAUTHORIZED = "401";
	private final String INTERNAL_SERVER_ERROR = "500";

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
		return new Components().addSecuritySchemes(TOKEN_NAME, initSecurityScheme())
			.addResponses(CREATED, getApiResponseByKey(CREATED))
			.addResponses(NO_CONTENT, getApiResponseByKey(NO_CONTENT))
			.addResponses(BAD_REQUEST, getApiResponseByKey(BAD_REQUEST))
			.addResponses(UNAUTHORIZED, getApiResponseByKey(UNAUTHORIZED))
			.addResponses(INTERNAL_SERVER_ERROR, getApiResponseByKey(INTERNAL_SERVER_ERROR))
			;
	}

	private ApiResponse getApiResponseByKey(String key) {
		switch (key) {
			case CREATED -> {
				return new ApiResponse().description("Created")
					.content(new Content().addMediaType("application/json", new MediaType()
						.example(
							ResponseJsonObject.builder()
								.code(ServiceStatusCode.CREATED)
								.data("{}")
								.expandMsg("msg")
								.build()
						)))
					;
			}
			case NO_CONTENT -> {
				return new ApiResponse().description("No Content")
					.content(new Content().addMediaType("application/json", new MediaType()
						.example(
							ResponseJsonObject.builder()
								.code(ServiceStatusCode.NO_CONTENT)
								.data("{}")
								.expandMsg("msg")
								.build()
						)))
					;
			}
			case BAD_REQUEST -> {
				return new ApiResponse().description("Bad Request")
					.content(new Content().addMediaType("application/json", new MediaType()
						.example(
							ResponseJsonObject.builder()
								.code(ServiceStatusCode.BAD_REQUEST)
								.data("{}")
								.expandMsg("msg")
								.build()
						)))
					;
			}
			case UNAUTHORIZED -> {
				return new ApiResponse().description("Unauthorized")
					.content(new Content().addMediaType("application/json", new MediaType()
						.example(
							ResponseJsonObject.builder()
								.code(ServiceStatusCode.UNAUTHORIZED)
								.data("{}")
								.expandMsg("msg")
								.build()
						)))
					;
			}

			case INTERNAL_SERVER_ERROR -> {
				return new ApiResponse().description("Internal Server Error")
					.content(new Content().addMediaType("application/json", new MediaType()
						.example(
							ResponseJsonObject.builder()
								.code(ServiceStatusCode.INTERVAL_SERVER_ERROR)
								.data("{}")
								.expandMsg("msg")
								.build()
						)))
					;
			}

		}
		return null;
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
