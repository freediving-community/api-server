package com.freediving.buddyservice.adapter.in.web.v1;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.application.port.in.CreateBuddyEventCommand;
import com.freediving.buddyservice.application.port.in.CreateBuddyEventUseCase;
import com.freediving.buddyservice.domain.CreatedBuddyEvent;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CreateBuddyEventControllerV1 {

	private final CreateBuddyEventUseCase createBuddyEventUseCase;

	@PostMapping("/buddy-event")
	public ResponseEntity<CreatedBuddyEvent> createBuddyEvent(@Valid @RequestBody CreateBuddyEventRequestV1 request) {
		// 1. JWT 유저 토큰에서 사용자 식별 ID 가져오기.
		Random random = new Random();
		Long userId = random.nextLong();

		CreatedBuddyEvent createdBuddyEvent = createBuddyEventUseCase.createBuddyEvent(CreateBuddyEventCommand.builder()
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
