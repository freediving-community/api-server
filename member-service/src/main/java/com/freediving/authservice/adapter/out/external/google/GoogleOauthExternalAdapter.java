package com.freediving.authservice.adapter.out.external.google;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.freediving.authservice.adapter.out.external.OauthResponse;
import com.freediving.authservice.application.port.out.MemberServiceFeignClient;
import com.freediving.authservice.application.port.out.OauthFeignPort;
import com.freediving.authservice.application.port.out.google.GoogleInfoFeignClient;
import com.freediving.authservice.application.port.out.google.GoogleTokenFeignClient;
import com.freediving.authservice.config.GoogleOauthConfig;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.ExternalSystemAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ExternalSystemAdapter
@RequiredArgsConstructor
@Slf4j
public class GoogleOauthExternalAdapter implements OauthFeignPort {

	private final GoogleOauthConfig googleOauthConfig;
	private final GoogleTokenFeignClient googleTokenFeignClient;
	private final GoogleInfoFeignClient googleInfoFeignClient;

	private final MemberServiceFeignClient memberServiceFeignClient;

	@Override
	public OauthType getOauthType() {
		return OauthType.GOOGLE;
	}

	@Override
	public String createRequestUrl() {
		String url = UriComponentsBuilder
			.fromUriString(googleOauthConfig.codeUri())
			.queryParam("client_id", googleOauthConfig.clientId())
			.queryParam("redirect_uri", googleOauthConfig.redirectUri())
			.queryParam("response_type", "code")
			.queryParam("scope", googleOauthConfig.scope())
			.toUriString();

		log.info("GOOGLE REQUEST URL : {} ", url);
		return url;
	}

	@Override
	public OauthUser fetch(String code) {
		GoogleTokenResponse googleTokenResponse = googleTokenFeignClient.postToken(tokenParamMap(code));
		log.info("GOOGLE TOKEN : {}", googleTokenResponse);
		GoogleInfoResponse googleInfoResponse = googleInfoFeignClient.postInfo(
			"Bearer " + googleTokenResponse.accessToken());
		log.info("GOOGLE INFO : {}", googleInfoResponse);

		OauthResponse oauthResponse = OauthResponse.of(getOauthType(), googleInfoResponse.email(),
			googleInfoResponse.picture());

		OauthUser oauthUser = OauthUser.from(oauthResponse);
		memberServiceFeignClient.postOauthUserInfo(oauthUser);
		return oauthUser;
	}

	private MultiValueMap<String, String> tokenParamMap(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", googleOauthConfig.clientId());
		params.add("client_secret", googleOauthConfig.clientSecret());
		params.add("code", code);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", googleOauthConfig.redirectUri());
		return params;
	}
}


