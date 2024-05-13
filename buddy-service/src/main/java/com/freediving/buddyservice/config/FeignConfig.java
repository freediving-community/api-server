package com.freediving.buddyservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 테스트 환경에서 Configuration 정보를 분리하기 위해 별도 생성
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2024-04-23
 **/
@Configuration
@EnableFeignClients(basePackages = "com.freediving")
public class FeignConfig {
}
