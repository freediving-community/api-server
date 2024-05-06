package com.freediving.communityservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.freediving.common.persistence.AuditableEntity;
import com.freediving.common.persistence.CreatedDateEntity;
import com.freediving.common.persistence.JpaAuditingConfig;
import com.freediving.common.persistence.UpdatedDateEntity;

@Configuration
@ComponentScan(
	basePackages = "com.freediving.common",
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = {
				AuditableEntity.class,
				CreatedDateEntity.class,
				JpaAuditingConfig.class,
				UpdatedDateEntity.class
			}
		)
	}
)
public class CommunityServiceCommonModuleScan {
}
