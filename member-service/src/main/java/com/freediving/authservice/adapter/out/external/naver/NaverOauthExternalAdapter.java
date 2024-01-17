package com.freediving.authservice.adapter.out.external.naver;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.freediving.authservice.adapter.out.external.OauthResponse;
import com.freediving.authservice.application.port.out.OauthFeignPort;
import com.freediving.authservice.application.port.out.naver.NaverInfoFeignClient;
import com.freediving.authservice.application.port.out.naver.NaverTokenFeignClient;
import com.freediving.authservice.config.NaverOauthConfig;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.ExternalSystemAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : Naver 로그인 요청 및 응답값 처리를 작업을 하는 Adapter
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31        sasca37       최초 생성
 */

@ExternalSystemAdapter
@RequiredArgsConstructor
@Slf4j
public class NaverOauthExternalAdapter implements OauthFeignPort {

	private final NaverOauthConfig naverOauthConfig;
	private final NaverTokenFeignClient naverTokenFeignClient;
	private final NaverInfoFeignClient naverInfoFeignClient;

	@Override
	public OauthType getOauthType() {
		return OauthType.NAVER;
	}

	@Override
	public String createRequestUrl() {
		String url = UriComponentsBuilder
			.fromUriString(naverOauthConfig.codeUri())
			.queryParam("response_type", "code")
			.queryParam("client_id", naverOauthConfig.clientId())
			.queryParam("redirect_uri", naverOauthConfig.redirectUri())
			.queryParam("state", naverOauthConfig.state())
			.build()
			.toUriString();

		log.info("NAVER createRequestURL : {} ", url);
		return url;
	}

	@Override
	public OauthUser fetch(String code) {
		NaverTokenResponse naverTokenResponse = naverTokenFeignClient.postToken(tokenParamMap(code));

		log.info("NAVER TOKEN {}", naverTokenResponse);

		NaverInfoResponse naverInfoResponse = naverInfoFeignClient.postInfo(
			"Bearer " + naverTokenResponse.accessToken());

		log.info("NAVER INFO {}", naverInfoResponse);

		OauthResponse oauthResponse = OauthResponse.of(getOauthType(), naverInfoResponse.response().email(),
			naverInfoResponse.response().profileImage());
		OauthUser oauthUser = OauthUser.from(oauthResponse);
		return oauthUser;
	}

	private MultiValueMap<String, String> tokenParamMap(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", naverOauthConfig.clientId());
		params.add("client_secret", naverOauthConfig.clientSecret());
		params.add("code", code);
		params.add("state", naverOauthConfig.state());
		return params;
	}
}
