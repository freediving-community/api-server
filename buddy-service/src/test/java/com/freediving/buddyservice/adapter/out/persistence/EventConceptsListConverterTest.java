package com.freediving.buddyservice.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.freediving.buddyservice.common.enumeration.EventConcept;

@ActiveProfiles("local")
class EventConceptsListConverterTest {

	@DisplayName("이벤트 컨셉 List 타입을 데이터베이스 타입(콤바 문자열)로 변환한다.")
	@Test
	void convertToDatabaseColumn() {
		EventConceptsListConverter converter = new EventConceptsListConverter();

		List<EventConcept> eventConcepts = List.of(EventConcept.LEVEL_UP, EventConcept.PHOTOGRAPHY,
			EventConcept.TRAINING);

		assertThat(converter.convertToDatabaseColumn(eventConcepts)).isEqualTo("LEVEL_UP,PHOTOGRAPHY,TRAINING");

	}

	@DisplayName("이벤트 컨셉 List가 null이면 데이터베이스 타입(콤바 문자열)로 변환하면 빈 문자열 값이다.")
	@Test
	void convertToDatabaseColumnCaseNullString() {
		EventConceptsListConverter converter = new EventConceptsListConverter();

		List<EventConcept> eventConcepts = null;

		assertThat(converter.convertToDatabaseColumn(eventConcepts)).isEqualTo("");

	}

	@DisplayName("이벤트 컨셉 List가 비어 있으면 데이터베이스 타입(콤바 문자열)로 변환하면 빈 문자열 값이다.")
	@Test
	void convertToDatabaseColumnCaseListEmpty() {
		EventConceptsListConverter converter = new EventConceptsListConverter();

		List<EventConcept> eventConcepts = List.of();

		assertThat(converter.convertToDatabaseColumn(eventConcepts)).isEqualTo("");

	}

	@DisplayName("이벤트 컨셉 데이터베이스 타입(콤바 문자열)을 Enum List 타입으로 변환한다.")
	@Test
	void convertToEntityAttribute() {
		EventConceptsListConverter converter = new EventConceptsListConverter();

		assertThat(converter.convertToEntityAttribute("LEVEL_UP,PHOTOGRAPHY,TRAINING")).isEqualTo(
			List.of(EventConcept.LEVEL_UP, EventConcept.PHOTOGRAPHY,
				EventConcept.TRAINING));

	}

	@DisplayName("이벤트 컨셉 데이터베이스 타입(콤바 문자열)가 빈 문자열 일때 Enum List 타입으로 변환하면 빈 List로 변환된다.")
	@Test
	void convertToEntityAttributeCaseEmptyString() {
		EventConceptsListConverter converter = new EventConceptsListConverter();

		assertThat(converter.convertToEntityAttribute("")).isEqualTo(
			List.of());

	}

	@DisplayName("이벤트 컨셉 데이터베이스 타입(콤바 문자열)가 null 일때 Enum List 타입으로 변환하면 빈 List로 변환된다.")
	@Test
	void convertToEntityAttributeCaseNullString() {
		EventConceptsListConverter converter = new EventConceptsListConverter();

		assertThat(converter.convertToEntityAttribute(null)).isEqualTo(
			List.of());

	}

	@DisplayName("이벤트 컨셉 데이터베이스 타입에서 Enum List로 변환할때 컨셉이름이 안맞는 경우 Exception 발생")
	@Test
	void convertToEntityAttributeWithIgnoredMismatches() {
		EventConceptsListConverter converter = new EventConceptsListConverter();

		assertThatThrownBy(() -> converter.convertToEntityAttribute("LEVEL_UP,PRAPHY,TRAINING"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(
				"No enum constant com.freediving.buddyservice.common.enumeration.EventConcept.PRAPHY");
	}

}