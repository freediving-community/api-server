package com.freediving.memberservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.freediving.memberservice.application.service.FindUserService;
import com.freediving.memberservice.exception.CustomAccessDeniedHandler;
import com.freediving.memberservice.exception.CustomAuthenticationEntryPoint;
import com.freediving.memberservice.filter.CustomAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : Spring Security 6.0 버전부터 WebConfigurerAdapter를 사용할 수 없고 component-based security 설정 필요
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	private final FindUserService findUserService;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/21
	 * @Param            : HttpSecurity
	 * @Return           : SecurityFilterChain 필터 체이닝
	 * @Description      : Security 6부터 Customizer 사용
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// JWT 인증 방식을 사용하기 때문에 CSRF 비활성화 (CSRF는 세션 기반 인증 공격)
		return http.csrf((csrf) -> csrf.disable())

			// 인증 / 인가에 대한 exception handling
			.exceptionHandling((exception) ->
				exception.authenticationEntryPoint(customAuthenticationEntryPoint)
					.accessDeniedHandler(customAccessDeniedHandler)
			)

			// JWT 인증 방식에 맞는 STATELESS 전략 지정
			.sessionManagement((session) ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			// 회원가입 / 로그인 / swagger 를 제외한 나머지 모든 request 헤더 검증
			.authorizeHttpRequests((request) ->
				request.requestMatchers("/v*/oauth/**", "/v*/service/users/register", "/v3/api-docs").permitAll()
					.anyRequest().authenticated()
			)

			// UsernamePassword 필터 적용 전 헤더 검증 작업 및 SecurityContext 등록
			.addFilterBefore(new CustomAuthenticationFilter(findUserService),
				UsernamePasswordAuthenticationFilter.class)

			.build();
	}
}
