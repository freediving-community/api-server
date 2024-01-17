package com.freediving.memberservice.adapter.out.persistence;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.freediving.memberservice.domain.OauthType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/16
 * @Description    : OauthTypeSetVO 를 Convert
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/16        sasca37       최초 생성
 */

@Converter
public class OauthTypeSetConverter implements AttributeConverter<OauthTypeSetVO, String> {

	public static final String DELIM = ",";

	@Override
	public String convertToDatabaseColumn(OauthTypeSetVO attribute) {
		if (ObjectUtils.isEmpty(attribute)) {
			return null;
		}
		return attribute.getOauthTypeSet().stream()
			.map(t -> t.getName())
			.collect(Collectors.joining(DELIM));
	}

	@Override
	public OauthTypeSetVO convertToEntityAttribute(String dbData) {
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
		String[] oauthTypeNames = dbData.split(DELIM);
		Set<OauthType> oauthTypeSet = Arrays.stream(oauthTypeNames)
			.map(n -> OauthType.from(n))
			.collect(Collectors.toSet());
		return new OauthTypeSetVO(oauthTypeSet);
	}
}
