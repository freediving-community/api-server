package com.freediving.memberservice.mapper.internal;

import com.freediving.common.domain.member.MemberLicenseInfo;
import com.freediving.common.domain.member.MemberLicenseInfo.MemberLicenseInfoBuilder;
import com.freediving.common.response.dto.member.MemberFindUserResponse;
import com.freediving.memberservice.adapter.in.web.dto.FindUserServiceResponse;
import com.freediving.memberservice.adapter.in.web.dto.LicenseInfo;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-11T01:13:31+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 21.0.1 (Amazon.com Inc.)"
)
public class FindUserMapperImpl implements FindUserMapper {

    @Override
    public MemberFindUserResponse toCommonFindUserResponse(FindUserServiceResponse findUserServiceResponse) {
        if ( findUserServiceResponse == null ) {
            return null;
        }

        MemberFindUserResponse memberFindUserResponse = new MemberFindUserResponse();

        memberFindUserResponse.setUserId( findUserServiceResponse.getUserId() );
        memberFindUserResponse.setUserStatus( findUserServiceResponse.getUserStatus() );
        memberFindUserResponse.setProfileImgUrl( findUserServiceResponse.getProfileImgUrl() );
        memberFindUserResponse.setNickname( findUserServiceResponse.getNickname() );
        memberFindUserResponse.setLicenseInfo( licenseInfoToMemberLicenseInfo( findUserServiceResponse.getLicenseInfo() ) );

        return memberFindUserResponse;
    }

    protected MemberLicenseInfo licenseInfoToMemberLicenseInfo(LicenseInfo licenseInfo) {
        if ( licenseInfo == null ) {
            return null;
        }

        MemberLicenseInfoBuilder memberLicenseInfo = MemberLicenseInfo.builder();

        memberLicenseInfo.freeDiving( licenseInfo.getFreeDiving() );
        memberLicenseInfo.scubaDiving( licenseInfo.getScubaDiving() );

        return memberLicenseInfo.build();
    }
}
