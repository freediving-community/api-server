package com.freediving.buddyservice.adapter.out.persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.freediving.buddyservice.common.enumeration.EventConcept;

import jakarta.persistence.AttributeConverter;

public class EventConceptsListConverter implements AttributeConverter<List<EventConcept>, String> {

	@Override
	public String convertToDatabaseColumn(List<EventConcept> divingPoolList) {
		if (divingPoolList == null || divingPoolList.isEmpty()) {
			return "";
		}
		return divingPoolList.stream()
			.map(EventConcept::name)
			.collect(Collectors.joining(","));
	}

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
