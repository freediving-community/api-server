package com.freediving.authservice.adapter.in.web;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.authservice.application.port.in.OauthUseCase;
import com.freediving.authservice.domain.OauthType;
import com.freediving.common.config.annotation.WebAdapter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/oauth")
@Slf4j
public class OauthController {

	private final OauthUseCase oauthUseCase;

	@GetMapping("/{socialType}")
	public ResponseEntity<Void> redirectAuthLogin(@PathVariable String socialType, HttpServletResponse response) {
		String redirectUrl = oauthUseCase.provideOauthType(OauthType.from(socialType));
		// TODO : 예외 처리 및 응답 포맷 결정 후 보완
		if (StringUtils.hasText(redirectUrl)) {
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/login/{socialType}")
	public ResponseEntity<UserLoginResponse> login(@PathVariable String socialType, @RequestParam("code") String code) {
		UserLoginResponse userLoginResponse = oauthUseCase.login(OauthType.from(socialType), code);
		return ResponseEntity.ok().body(userLoginResponse);
	}

	// TODO : 액세스 토큰 재발급
}
