package com.freediving.buddyservice.divingpool.data.dao;

import com.freediving.common.persistence.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private String divingPoolId;

	@Column(nullable = false)
	private String divingPoolName;

	@Column(length = 255, nullable = false)
	private String address;

	@Column(length = 255, nullable = false)
	private String description;

	@Column(nullable = false)
	private Boolean isVisible;

	@Column(nullable = false)
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
