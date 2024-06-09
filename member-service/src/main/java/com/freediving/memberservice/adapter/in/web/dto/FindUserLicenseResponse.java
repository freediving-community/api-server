package com.freediving.memberservice.adapter.in.web.dto;

import java.util.List;

import com.freediving.common.domain.member.FreeDiving;
import com.freediving.common.domain.member.RoleLevel;
import com.freediving.memberservice.adapter.out.dto.UserReviewResponse;
import com.freediving.memberservice.adapter.out.dto.UserStoryResponse;
import com.freediving.memberservice.domain.User;
import com.freediving.memberservice.domain.UserLicense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    : 다이버 정보 조회 요청에 대한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Schema(description = "다이버 정보 조회 응답 DTO")
public class FindUserLicenseResponse {
	private List<FindUserLicense> licenseList;
}
