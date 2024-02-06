package com.freediving.authservice.adapter.in.web;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
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
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Oauth", description = "소셜 로그인 작업 수행")
public class OauthController {

	private final OauthUseCase oauthUseCase;

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            : 소셜 타입 정보 (Google, Kakao, Naver)
	 * @Return           : 소셜 로그인 페이지 Redirect
	 * @Description      : 소셜별 리다이렉트 URL 정보를 받아 클라이언트 페이지에 Redirect
	 */
	@Operation(summary = "소셜 별 로그인 팝업 요청 API"
		, description = "사용자가 소셜 로그인 버튼을 눌렀을 때 해당 API에 요청하여 소셜별 Auth Server에서 "
		+ "Code 정보를 얻는 소셜 로그인 팝업 URL로 리다이렉트한다. <br/><br/> "
		+ "socialType : google, kakao, naver <br/><br/>"
		+ "Client Redirect URL : (/auth/kakao, /auth/google, /auth/naver)",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - socialType 정보 오류"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류")
		})
	@GetMapping("/{socialType}")
	public ResponseEntity<ResponseJsonObject<Void>> redirectAuthLogin(@PathVariable String socialType,
		HttpServletResponse response) {
		String redirectUrl = oauthUseCase.provideOauthType(OauthType.from(socialType));
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			log.error("REDIRECT ERROR : {} ", e);
			throw new BuddyMeException(ServiceStatusCode.INTERVAL_SERVER_ERROR, "redirect 작업 중 서버 오류가 발생했습니다.");
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            : 소셜 타입명 (대소문자 구분 X), 소셜로 부터 얻은 code
	 * @Return           : UserLoginResponse (유저 기본 정보, JWT 토큰, 최초 가입 여부)
	 * @Description      : Oauth 로그인 완료 후 얻은 정보를 MemberService 에 전송 후 결과값 반환
	 */
	@Operation(summary = "소셜 로그인 후 Auth Server로 부터 사용자 정보를 받아 애플리케이션에 로그인하는 API"
		, description = "사용자 로그인 후 발급되는 Code 정보를 요청하여, 사용자 정보 및 JWT 발급 정보를 응답받는다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
			@ApiResponse(responseCode = "400", description = "실패 - request 정보 오류"),
			@ApiResponse(responseCode = "500", description = "실패 - 서버 오류")
		})
	@GetMapping("/login/{socialType}")
	public ResponseEntity<ResponseJsonObject<UserLoginResponse>> login(@PathVariable String socialType,
		@RequestParam("code") String code) {
		OauthUser oauthUser = oauthUseCase.login(OauthType.from(socialType), code);

		UserLoginResponse userLoginResponse = UserLoginResponse.from(oauthUser);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Token", oauthUser.getAccessToken());
		headers.add("Refresh-Token", oauthUser.getRefreshToken());

		ResponseJsonObject<UserLoginResponse> response = new ResponseJsonObject(ServiceStatusCode.OK,
			userLoginResponse);
		return ResponseEntity.ok().headers(headers).body(response);
	}

}
