package com.freediving.communityservice.config;

import org.springframework.core.convert.converter.Converter;

import com.freediving.communityservice.adapter.out.persistence.constant.BoardType;

public class StringToEnumConverter implements Converter<String, BoardType> {
	@Override
	public BoardType convert(String source) {
		return BoardType.valueOf(source.toUpperCase().replace("-", "_"));
	}
}
