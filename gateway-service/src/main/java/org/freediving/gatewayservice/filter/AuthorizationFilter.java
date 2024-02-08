package org.freediving.gatewayservice.filter;

import org.freediving.exception.GatewayException;
import org.freediving.gatewayservice.adapter.in.web.JwtProvider;
import org.freediving.gatewayservice.domain.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

	public static final String INVALID_JWT_TOKEN = "JWT 정보가 유효하지 않습니다.";
	public static final String EXPIRED_JWT_TOKEN = "만료된 JWT 입니다.";
	private static final String NOT_FOUND_JWT_TOKEN = "JWT 정보가 없습니다.";
	private final ObjectMapper objectMapper;

	@Value("${jwt.key}")
	private String key;

	public AuthorizationFilter(ObjectMapper objectMapper) {
		super(Config.class);
		this.objectMapper = objectMapper;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("request id: {}, request uri: {}", request.getId(), request.getURI());

			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return handleUnAuthorized(exchange, NOT_FOUND_JWT_TOKEN, HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String token = authorizationHeader.replace("Bearer", "");
			log.info("JWT TOKEN : {}", token);
			String errorMessage = JwtProvider.getExpiredOrMalformedMessage(token, key);

			if (errorMessage != null) {
				return handleUnAuthorized(exchange, errorMessage, HttpStatus.UNAUTHORIZED);
			}

			Token extractToken = JwtProvider.extractToken(token, key);
			String userId = extractToken.userId();

			ServerHttpRequest modifiedRequest = request.mutate()
				.header("User-Id", userId)
				.build();

			return chain.filter(exchange.mutate().request(modifiedRequest).build()).then(Mono.fromRunnable(
				() -> log.info("response status code: {}", response.getStatusCode()))
			);
		});
	}

	private Mono<Void> handleUnAuthorized(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		GatewayException errorResponse = new GatewayException(httpStatus.value(), "{}", message);
		try {
			byte[] data = objectMapper.writeValueAsBytes(errorResponse);
			DataBuffer buffer = response.bufferFactory().wrap(data);
			log.error("handling error: {}", message);
			return response.writeWith(Mono.just(buffer));
		} catch (JsonProcessingException e) {
			log.error("json processing error : {}", e);
			return response.setComplete();
		}
	}

	public static class Config {
	}
}
