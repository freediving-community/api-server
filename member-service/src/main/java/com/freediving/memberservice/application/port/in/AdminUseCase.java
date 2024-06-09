package com.freediving.memberservice.application.port.in;

import java.util.List;

import com.freediving.memberservice.adapter.in.web.dto.FindMyPageResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserInfoResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserLicenseResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;
import com.freediving.memberservice.domain.User;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    : 관리자 UseCase
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09        sasca37       최초 생성
 */

public interface AdminUseCase {

	FindUserLicenseResponse findUserLicenseSubmitList();

	void updateUserLicenseStatus(UpdateUserLicenseStatusCommand command);
}
