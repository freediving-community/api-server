package com.freediving.buddyservice.adapter.out.persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.freediving.buddyservice.common.enumeration.EventConcept;

import jakarta.persistence.AttributeConverter;

/**
 * 데이터베이스 EventConcepts의 문자열(콤마분리)를 List enum형식으로 컨버팅 하는 객체.
 *   문자열 -> enum List
 *   enum List -> 문자열
 * @author 준희조
 * @version 1.0.0  작성일 2023-12-27
 */
public class EventConceptsListConverter implements AttributeConverter<List<EventConcept>, String> {

	/**
	 * List EventConcept를 DataBase Column (콤바분리 문자열)으로 변환한다.
	 *
	 * @param eventConcepts eventConcept Enum List
	 * @return 콤마분리 문자열로 변환된 문자열
	 */
	@Override
	public String convertToDatabaseColumn(List<EventConcept> eventConcepts) {
		if (eventConcepts == null || eventConcepts.isEmpty()) {
			return "";
		}
		return eventConcepts.stream()
			.map(EventConcept::name)
			.collect(Collectors.joining(","));
	}

	/**
	 * ataBase Column (콤바분리 문자열)를 List EventConcept으로 변환한다.
	 *
	 * @param dbData 콤마분리 문자열로 변환된 문자열
	 * @return eventConcept Enum List
	 */
	@Override
	public List<EventConcept> convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.trim().isEmpty()) {
			return Collections.emptyList();
		}
		return Arrays.stream(dbData.split(","))
			.map(EventConcept::valueOf)
			.collect(Collectors.toList());
	}
}
