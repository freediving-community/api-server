package com.freediving.authservice.application.port.out.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freediving.authservice.adapter.out.external.kakao.KaKaoTokenResponse;

@FeignClient(name = "kakaoTokenFeignClient", url = "${oauth.kakao.token_uri}")
public interface KakaoTokenFeignClient {
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	KaKaoTokenResponse postToken(@RequestParam MultiValueMap<String, String> params);

}
