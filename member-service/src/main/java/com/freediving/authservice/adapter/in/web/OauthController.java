package com.freediving.authservice.adapter.in.web;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.adapter.in.web.dto.UserLoginResponse;
import com.freediving.authservice.application.port.in.OauthUseCase;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.WebAdapter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/17
 * @Description    : Oauth (Google, Kakao, Naver) 별 로그인 및 JWT 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/17        sasca37       최초 생성
 */

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/oauth")
@Slf4j
public class OauthController {

	private final OauthUseCase oauthUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            : 소셜 타입 정보 (Google, Kakao, Naver)
	 * @Return           : 소셜 로그인 페이지 Redirect
	 * @Description      : 소셜별 리다이렉트 URL 정보를 받아 클라이언트 페이지에 Redirect
	 */
	@GetMapping("/{socialType}")
	public ResponseEntity<Void> redirectAuthLogin(@PathVariable String socialType, HttpServletResponse response) {
		String redirectUrl = oauthUseCase.provideOauthType(OauthType.from(socialType));
		// TODO : 예외 처리 및 응답 포맷 결정 후 보완
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/12/31
	 * @Param            : 소셜 타입명 (대소문자 구분 X), 소셜로 부터 얻은 code
	 * @Return           : UserLoginResponse (유저 기본 정보, JWT 토큰, 최초 가입 여부)
	 * @Description      : Oauth 로그인 완료 후 얻은 정보를 MemberService 에 전송 후 결과값 반환
	 */
	@GetMapping("/login/{socialType}")
	public ResponseEntity<UserLoginResponse> login(@PathVariable String socialType, @RequestParam("code") String code) {
		OauthUser oauthUser = oauthUseCase.login(OauthType.from(socialType), code);
		UserLoginResponse userLoginResponse = UserLoginResponse.from(oauthUser);
		return ResponseEntity.ok().body(userLoginResponse);
	}

}
