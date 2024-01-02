package com.freediving.authservice.application.port.out;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.freediving.authservice.domain.OauthType;
import com.freediving.authservice.domain.OauthUser;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OauthTemplate implements InitializingBean {
	private final List<OauthFeignPort> feignPortList;
	private Map<OauthType, OauthFeignPort> feignPortMap;

	public String doRequest(OauthType oauthType) {
		return Optional.ofNullable(feignPortMap.get(oauthType))
			.filter(port -> port.canRequest(oauthType))
			.map(OauthRedirectUrlPort::createRequestUrl)
			.orElseThrow(() -> new NoSuchElementException("No OauthRedirectUrlPort found for " + oauthType));
	}

	public OauthUser doPostTokenAndGetInfo(OauthType oauthType, String code) {
		return Optional.ofNullable(feignPortMap.get(oauthType))
			.filter(port -> port.canRequest(oauthType))
			.map(port -> port.fetch(code))
			.orElseThrow(() -> new NoSuchElementException("No OauthFeignPort found for " + oauthType));
	}

	@Override
	public void afterPropertiesSet() {
		feignPortMap = feignPortList.stream()
			.collect(Collectors.toMap(OauthFeignPort::getOauthType, Function.identity()));
	}

}
