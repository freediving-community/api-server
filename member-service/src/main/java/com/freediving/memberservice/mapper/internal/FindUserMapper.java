package com.freediving.memberservice.mapper.internal;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.freediving.common.response.dto.member.MemberFindUserResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/26
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/26        sasca37       최초 생성
 */

@Mapper
public interface FindUserMapper {
	FindUserMapper INSTANCE = Mappers.getMapper(FindUserMapper.class);

	MemberFindUserResponse toCommonFindUserResponse(FindUserServiceResponse findUserServiceResponse);

}
