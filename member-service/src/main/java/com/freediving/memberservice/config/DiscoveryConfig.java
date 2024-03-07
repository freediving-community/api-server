package com.freediving.memberservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/27
 * @Description    : 테스트 환경에서 Configuration 정보를 분리하기 위해 별도 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/27        sasca37       최초 생성
 */

@Configuration
@EnableDiscoveryClient
public class DiscoveryConfig {
}
