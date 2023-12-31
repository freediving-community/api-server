package com.freediving.authservice.application.port.out.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.freediving.authservice.adapter.out.external.kakao.KakaoInfoResponse;

@FeignClient(name = "kakaoInfoFeignClient", url = "${oauth.kakao.resource_uri}")
public interface KakaoInfoFeignClient {

	@GetMapping
	KakaoInfoResponse postInfo(@RequestHeader(name = "Authorization") String token);
}
