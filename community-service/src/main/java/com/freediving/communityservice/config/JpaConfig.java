package com.freediving.communityservice.config;

import java.util.Optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.freediving.communityservice.adapter.in.web.UserProvider;
import com.freediving.communityservice.adapter.in.web.UserProviderAuditorAware;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@EnableJpaAuditing
@RequiredArgsConstructor
@Configuration
public class JpaConfig {

	private final ObjectProvider<UserProvider> userProvider;

	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new UserProviderAuditorAware(userProvider);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}
