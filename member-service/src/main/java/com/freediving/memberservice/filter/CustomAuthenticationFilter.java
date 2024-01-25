package com.freediving.memberservice.filter;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.freediving.memberservice.application.port.in.FindUserQuery;
import com.freediving.memberservice.application.service.FindUserService;
import com.freediving.memberservice.domain.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : UsernamePasswordAuthenticationFilter 동작 전에 헤더 검증
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

	private final FindUserService findUserService;

	private static final String USER_ID = "User-Id";
	private static final String ROLE_LEVEL = "Role-Level";

	private static List<String> ignorePathList = List.of("/oauth", "/service/users/register", "/v3/api-docs");

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/22
	 * @Param            : HttpServlet
	 * @Return           : void
	 * @Description      : 1. Security Config 에서 permitAll()을 사용하여 경로를 지정하여도,
	 *                     모든 필터는 처리하기 때문에 ignorePathList 로 헤더 검증 제외 로직 추가
	 *                     (permitAll은 SecurityContext에 인증 정보가 없더라도 접근 허용)
	 *
	 *                     2. 헤더 내 필요한 정보를 추출하여 User 정보 SecurityContext 등록
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String requestUrl = request.getRequestURL().toString();

		if (isIgnorePath(requestUrl)) {
			filterChain.doFilter(request, response);
			return;
		}

		final Long userId = Long.valueOf(request.getHeader(USER_ID));
		final String roleLevel = request.getHeader(ROLE_LEVEL);

		if (userId == null || StringUtils.isEmpty(roleLevel)) {
			log.error("Request header is invalid {}", requestUrl);
			filterChain.doFilter(request, response);
			return;
		}
		FindUserQuery findUserQuery = FindUserQuery.builder().userId(userId).build();
		User user = findUserService.findUserById(findUserQuery);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			user, null, null
		);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private boolean isIgnorePath(String requestUrl) {
		return (ignorePathList.stream().anyMatch(requestUrl::contains)) ? true : false;
	}

}
