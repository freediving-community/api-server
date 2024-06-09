package com.freediving.memberservice.application.port.out;

import com.freediving.memberservice.adapter.in.web.dto.FindUserLicenseResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserResponse;
import com.freediving.memberservice.application.port.in.UpdateUserInfoCommand;
import com.freediving.memberservice.application.port.in.UpdateUserLicenseStatusCommand;

/**
 * @Author         : sasca37
 * @Date           : 2024/06/09
 * @Description    : 관리자 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/06/09       sasca37       최초 생성
 */

public interface AdminPort {

	FindUserLicenseResponse findUserLicenseSubmitList();

	void updateUserLicenseStatus(UpdateUserLicenseStatusCommand command);
}
