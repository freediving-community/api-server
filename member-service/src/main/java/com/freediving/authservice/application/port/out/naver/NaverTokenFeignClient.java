package com.freediving.authservice.application.port.out.naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freediving.authservice.adapter.out.external.naver.NaverTokenResponse;

@FeignClient(name = "naverTokenFeignClient", url = "${oauth.naver.token_uri}")
public interface NaverTokenFeignClient {

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	NaverTokenResponse postToken(@RequestParam MultiValueMap<String, String> params);

}
