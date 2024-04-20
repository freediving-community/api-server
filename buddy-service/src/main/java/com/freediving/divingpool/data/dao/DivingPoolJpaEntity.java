package com.freediving.divingpool.data.dao;

import com.freediving.common.enumerate.DivingPool;
import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Table(name = "diving_pool", indexes = {
	@Index(name = "idx_divingPoolId", columnList = "divingPoolId")
})
public class DivingPoolJpaEntity extends AuditableEntity {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "diving_pool_id", nullable = false)
	private DivingPool divingPoolId;

	@Column(name = "diving_pool_name", nullable = false)
	private String divingPoolName;

	@Column(name = "address", length = 255, nullable = false)
	private String address;

	@Column(name = "simple_address", length = 255, nullable = false)
	private String simpleAddress; // 새로 추가된 필드

	@Column(name = "operating_hours", length = 255, nullable = false)
	private String operatingHours;

	@Column(name = "price_info", length = 255, nullable = false)
	private String priceInfo;

	@Column(name = "website_url", length = 255, nullable = false)
	private String websiteUrl;

	@Column(name = "recommended_level", length = 50, nullable = false)
	private String recommendedLevel;

	@Column(name = "depth", length = 50, nullable = false)
	private String depth;

	@Column(name = "description", length = 255, nullable = false)
	private String description;

	@Column(name = "is_visible", nullable = false)
	private Boolean isVisible;

	@Column(name = "display_order", nullable = false)
	private Integer displayOrder;

	@Override
	public String toString() {
		return "DivingPoolJpaEntity{" +
			"divingPoolId='" + divingPoolId + '\'' +
			", divingPoolName=" + divingPoolName +
			", address='" + address + '\'' +
			", description='" + description + '\'' +
			", isVisible=" + isVisible +
			", displayOrder=" + displayOrder +
			'}';
	}
}
