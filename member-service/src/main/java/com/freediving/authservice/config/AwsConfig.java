package com.freediving.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : AWS 관련 Credentials Configuration
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

	private final AwsConfigProperties awsConfigProperties;

	@Bean
	@Primary
	public BasicAWSCredentials credentialsProvider() {
		AwsConfigProperties.Credentials credentials = awsConfigProperties.credentials();
		return new BasicAWSCredentials(credentials.accessKey(), credentials.secretKey());
	}

	@Bean
	public AmazonS3 amazonS3() {
		AmazonS3 s3Builder = AmazonS3ClientBuilder
			.standard().withRegion(awsConfigProperties.region().info())
			.withCredentials(new AWSStaticCredentialsProvider(credentialsProvider()))
			.build();
		return s3Builder;
	}
}
