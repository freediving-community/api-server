package com.freediving.buddyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(basePackages = {"com.freediving.buddyservice", "com.freediving.common"})
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableCaching
public class BuddyServiceApplication {//push test1

	public static void main(String[] args) {
		SpringApplication.run(BuddyServiceApplication.class, args);
	}
}