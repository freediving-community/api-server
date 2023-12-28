package com.freediving.buddyservice.adapter.in.web.v1;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventUseCase;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;
import com.freediving.common.config.annotation.WebAdapter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 외부 web으로의 RestAPI 요청을 받아내는 Adapter 역할
 *
 * @author 준희조
 * @version 1.0.0
 * 작성일 2023-12-27
 **/
@WebAdapter
@RestController
@RequiredArgsConstructor
public class CreateBuddyEventControllerV1 {

	private final CreateBuddyEventUseCase createBuddyEventUseCase;

	@PostMapping("/buddy-event")
	public ResponseEntity<CreatedBuddyEvent> createBuddyEvent(@Valid @RequestBody CreateBuddyEventRequestV1 request) {
		// 1. JWT 유저 토큰에서 사용자 식별 ID 가져오기.
		Random random = new Random();
		Long userId = random.nextLong();

		CreatedBuddyEvent createdBuddyEvent = createBuddyEventUseCase.createBuddyEventV1(
			CreateBuddyEventCommand.builder()
				.userId(userId)
				.eventStartDate(request.getEventStartDate())
				.eventEndDate(request.getEventEndDate())
				.participantCount(request.getParticipantCount())
				.eventConcepts(request.getEventConcepts())
				.carShareYn(request.getCarShareYn())
				.comment(request.getComment())
				.build());

		// 2. Command 요청 및 응답 리턴.
		return ResponseEntity.ok(createdBuddyEvent);
	}
}
