package com.freediving.memberservice.config;

import java.util.List;

import com.freediving.common.response.ResponseJsonObject;
import com.freediving.common.response.dto.member.MemberFindUserResponse;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/15
 * @Description    : Swagger Generic Type Response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/15        sasca37       최초 생성
 */
public class SwaggerResponse {

	public class RespMemberFindUserResponse extends ResponseJsonObject<List<MemberFindUserResponse>> {
	}

}
