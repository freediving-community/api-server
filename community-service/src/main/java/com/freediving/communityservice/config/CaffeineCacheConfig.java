package com.freediving.communityservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.freediving.communityservice.config.type.CacheType;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {
	@Bean
	public List<CaffeineCache> caffeineCaches() {
		return Arrays.stream(CacheType.values())
			.map(cache -> {
					if (cache.getExpireAfterAccessSeconds() == -1L) {
						return new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
							.expireAfterWrite(cache.getExpiredSecondAfterWriteSeconds(), TimeUnit.SECONDS)
							.maximumSize(cache.getMaximumSize())
							.build());
					}
					return new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
						.expireAfterWrite(cache.getExpiredSecondAfterWriteSeconds(), TimeUnit.SECONDS)
						.expireAfterAccess(cache.getExpireAfterAccessSeconds(), TimeUnit.SECONDS)
						.maximumSize(cache.getMaximumSize())
						.build());
				}
			)
			.toList();
	}

	@Bean
	public CacheManager cacheManager(List<CaffeineCache> caffeineCaches) {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caffeineCaches);

		return cacheManager;
	}
}
