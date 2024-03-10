package com.freediving.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : AWS Config 정보 바인딩
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */
@ConfigurationProperties(prefix = "cloud.aws")
public record AwsConfigProperties(
	S3 s3,
	Region region,
	Credentials credentials
) {
	public record S3(String bucket, String cloudFront) {
	}

	public record Credentials(String accessKey, String secretKey) {
	}

	public record Region(String info) {
	}

}
