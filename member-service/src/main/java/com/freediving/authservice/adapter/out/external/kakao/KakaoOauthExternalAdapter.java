package com.freediving.authservice.adapter.out.external.kakao;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.freediving.authservice.adapter.out.external.OauthResponse;
import com.freediving.authservice.application.port.out.MemberServiceFeignClient;
import com.freediving.authservice.application.port.out.OauthFeignPort;
import com.freediving.authservice.application.port.out.kakao.KakaoInfoFeignClient;
import com.freediving.authservice.application.port.out.kakao.KakaoTokenFeignClient;
import com.freediving.authservice.config.KakaoOauthConfig;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.ExternalSystemAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ExternalSystemAdapter
@RequiredArgsConstructor
@Slf4j
public class KakaoOauthExternalAdapter implements OauthFeignPort {

	private final KakaoOauthConfig kakaoOauthConfig;

	private final KakaoTokenFeignClient kakaoTokenFeignClient;
	private final KakaoInfoFeignClient kakaoInfoFeignClient;
	private final MemberServiceFeignClient memberServiceFeignClient;

	@Override
	public OauthType getOauthType() {
		return OauthType.KAKAO;
	}

	@Override
	public String createRequestUrl() {
		return UriComponentsBuilder
			.fromUriString(kakaoOauthConfig.codeUri())
			.queryParam("response_type", "code")
			.queryParam("client_id", kakaoOauthConfig.clientId())
			.queryParam("redirect_uri", kakaoOauthConfig.redirectUri())
			.queryParam("scope", String.join(",", kakaoOauthConfig.scope()))
			.toUriString();
	}

	@Override
	public OauthUser fetch(String code) {
		KaKaoTokenResponse kaKaoTokenResponse = kakaoTokenFeignClient.postToken(tokenParamMap(code));
		log.info("KAKAO TOKEN RESPONSE : {}", kaKaoTokenResponse);

		KakaoInfoResponse kakaoInfoResponse = kakaoInfoFeignClient.postInfo(
			"Bearer " + kaKaoTokenResponse.accessToken());
		log.info("KAKAO INFO RESPONSE : {}", kakaoInfoResponse);

		OauthResponse oauthResponse = OauthResponse.of(getOauthType(), kakaoInfoResponse.kakaoAccount().email(),
			kakaoInfoResponse.kakaoAccount().profile().profileImageUrl());
		OauthUser oauthUser = OauthUser.from(oauthResponse);
		memberServiceFeignClient.postOauthUserInfo(oauthUser);

		return oauthUser;
	}

	private MultiValueMap<String, String> tokenParamMap(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", kakaoOauthConfig.clientId());
		params.add("redirect_uri", kakaoOauthConfig.redirectUri());
		params.add("client_secret", kakaoOauthConfig.clientSecret());
		params.add("code", code);
		return params;
	}
}
