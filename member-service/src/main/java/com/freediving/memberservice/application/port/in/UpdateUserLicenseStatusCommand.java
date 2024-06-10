package com.freediving.memberservice.application.port.in;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import com.freediving.common.SelfValidating;
import com.freediving.memberservice.domain.DiveType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateUserLicenseStatusCommand extends SelfValidating<UpdateUserLicenseStatusCommand> {

	private Long adminUserId;
	private Long licenseId;
	private Integer licenseLevel;
	private String orgName;
	private Boolean confirmTF;
	private String rejectContent;


	public UpdateUserLicenseStatusCommand(Long adminUserId, Long licenseId, Integer licenseLevel, String orgName, Boolean confirmTF, String rejectContent) {
		this.adminUserId = adminUserId;
		this.licenseId = licenseId;
		this.licenseLevel = licenseLevel;
		this.orgName = orgName;
		this.confirmTF = confirmTF;
		this.rejectContent = rejectContent;
		this.validateSelf();
	}
}
