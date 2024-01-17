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

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : 요청 온 소셜 로그인 별 타입 검증, 토큰 및 인증 정보 등의 요청 작업 Template
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31        sasca37       최초 생성
 */

@Component
@RequiredArgsConstructor
public class OauthTemplate implements InitializingBean {
	private final List<OauthFeignPort> feignPortList;
	private Map<OauthType, OauthFeignPort> feignPortMap;

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            :
	 * @Return           : 로그인 팝업 URL
	 * @Description      : 요청온 소셜 타입을 검증하고, 팝업 URL을 가져올 수 있는 경우 반환
	 */
	public String doRequest(OauthType oauthType) {
		return Optional.ofNullable(feignPortMap.get(oauthType))
			.filter(port -> port.canRequest(oauthType))
			.map(OauthRedirectUrlPort::createRequestUrl)
			.orElseThrow(() -> new NoSuchElementException("No OauthRedirectUrlPort found for " + oauthType));
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            :
	 * @Return           : OauthUser
	 * @Description      : 사용자 정보 요청을 위한 토큰 정보를 가져와서 요청 후 유저 정보 반환
	 */
	public OauthUser doPostTokenAndGetInfo(OauthType oauthType, String code) {
		return Optional.ofNullable(feignPortMap.get(oauthType))
			.filter(port -> port.canRequest(oauthType))
			.map(port -> port.fetch(code))
			.orElseThrow(() -> new NoSuchElementException("No OauthFeignPort found for " + oauthType));
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2023/12/31
	 * @Param            :
	 * @Return           : void
	 * @Description      : 빈 초기화 시점에 Port에 저장되어 있는 구현체 정보를 불러와 요청 온 타입 확인 및 검증
	 */
	@Override
	public void afterPropertiesSet() {
		feignPortMap = feignPortList.stream()
			.collect(Collectors.toMap(OauthFeignPort::getOauthType, Function.identity()));
	}

}
