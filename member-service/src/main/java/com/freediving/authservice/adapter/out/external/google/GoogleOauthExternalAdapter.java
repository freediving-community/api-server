package com.freediving.authservice.adapter.out.external.google;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.freediving.authservice.adapter.out.external.OauthResponse;
import com.freediving.authservice.application.port.out.OauthFeignPort;
import com.freediving.authservice.application.port.out.google.GoogleInfoFeignClient;
import com.freediving.authservice.application.port.out.google.GoogleTokenFeignClient;
import com.freediving.authservice.config.GoogleOauthConfig;
import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;
import com.freediving.common.config.annotation.ExternalSystemAdapter;
import com.freediving.common.handler.exception.BuddyMeException;
import com.freediving.common.response.enumerate.ServiceStatusCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : Google 로그인 요청 및 응답값 처리를 작업을 하는 Adapter
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31        sasca37       최초 생성
 */

@ExternalSystemAdapter
@RequiredArgsConstructor
@Slf4j
public class GoogleOauthExternalAdapter implements OauthFeignPort {

	private final GoogleOauthConfig googleOauthConfig;
	private final GoogleTokenFeignClient googleTokenFeignClient;
	private final GoogleInfoFeignClient googleInfoFeignClient;

	@Override
	public OauthType getOauthType() {
		return OauthType.GOOGLE;
	}

	@Override
	public String createRequestUrl(String profile) {
		String url = UriComponentsBuilder
			.fromUriString(googleOauthConfig.codeUri())
			.queryParam("client_id", googleOauthConfig.clientId())
			.queryParam("redirect_uri", getRedirectUriByProfile(profile))
			.queryParam("response_type", "code")
			.queryParam("scope", googleOauthConfig.scope())
			.toUriString();

		log.info("GOOGLE REQUEST URL : {} ", url);
		return url;
	}

	@Override
	public OauthUser fetch(String code, String profile) {
		GoogleTokenResponse googleTokenResponse = googleTokenFeignClient.postToken(tokenParamMap(code, profile));
		log.info("GOOGLE TOKEN : {}", googleTokenResponse);
		GoogleInfoResponse googleInfoResponse = googleInfoFeignClient.postInfo(
			"Bearer " + googleTokenResponse.accessToken());
		log.info("GOOGLE INFO : {}", googleInfoResponse);

		OauthResponse oauthResponse = OauthResponse.of(getOauthType(), googleInfoResponse.email(),
			googleInfoResponse.picture(), googleInfoResponse.id());

		OauthUser oauthUser = OauthUser.from(oauthResponse);
		return oauthUser;
	}

	@Override
	public String getRedirectUriByProfile(String profile) {
		if (StringUtils.equals(profile, "local")) {
			return googleOauthConfig.localRedirectUri();
		} else if (StringUtils.equals(profile, "dev")) {
			return googleOauthConfig.devRedirectUri();
		} else if (StringUtils.equals(profile, "prd")) {
			return googleOauthConfig.prdRedirectUri();
		} else {
			throw new BuddyMeException(ServiceStatusCode.BAD_REQUEST, "프로필 정보가 유효하지 않습니다.");
		}
	}

	private MultiValueMap<String, String> tokenParamMap(String code, String profile) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", googleOauthConfig.clientId());
		params.add("client_secret", googleOauthConfig.clientSecret());
		params.add("code", code);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", getRedirectUriByProfile(profile));
		return params;
	}

}


