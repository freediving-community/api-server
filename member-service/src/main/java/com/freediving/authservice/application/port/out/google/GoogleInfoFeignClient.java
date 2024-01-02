package com.freediving.authservice.application.port.out.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.freediving.authservice.adapter.out.external.google.GoogleInfoResponse;

@FeignClient(name = "googleInfoFeignClient", url = "${oauth.google.resource_uri}")
public interface GoogleInfoFeignClient {

	@GetMapping
	GoogleInfoResponse postInfo(@RequestHeader(name = "Authorization") String token);
}
