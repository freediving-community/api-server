package com.freediving.authservice.application.port.out.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freediving.authservice.adapter.out.external.google.GoogleTokenResponse;

@FeignClient(name = "googleTokenFeignClient", url = "${oauth.google.token_uri}")
public interface GoogleTokenFeignClient {

	@PostMapping(path = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	GoogleTokenResponse postToken(@RequestParam MultiValueMap<String, String> params);
}
