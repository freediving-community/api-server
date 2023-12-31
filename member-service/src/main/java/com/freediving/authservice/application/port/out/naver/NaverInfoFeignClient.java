package com.freediving.authservice.application.port.out.naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.freediving.authservice.adapter.out.external.naver.NaverInfoResponse;

@FeignClient(name = "naverInfoFeignClient", url = "${oauth.naver.resource_uri}")
public interface NaverInfoFeignClient {

	@GetMapping
	NaverInfoResponse postInfo(@RequestHeader(name = "Authorization") String token);
}
