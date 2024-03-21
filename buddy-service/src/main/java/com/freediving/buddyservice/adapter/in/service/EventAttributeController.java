package com.freediving.buddyservice.adapter.in.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.service.GetEventConceptsUseCase;
import com.freediving.buddyservice.domain.EventConceptsResponse;
import com.freediving.common.config.annotation.WebAdapter;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/attribute")
@Tag(name = "Attribute", description = "버디 이벤트 속성 API")
public class EventAttributeController {

	private final GetEventConceptsUseCase getEventConceptsUseCase;

	@GetMapping("")
	public ResponseEntity<EventConceptsResponse> getEventConcepts() {

		try {

			// 커멘트 생성 후 UseCase 전달

			return ResponseEntity.ok(null);
		} catch (Exception e) {
			throw e;
		}

	}

}
