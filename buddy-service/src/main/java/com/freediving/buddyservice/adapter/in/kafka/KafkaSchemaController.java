package com.freediving.buddyservice.adapter.in.kafka;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.buddyservice.adapter.in.kafka.dto.ConceptPreference;
import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;
import com.freediving.common.enumerate.DivingPool;
import com.freediving.divingpool.data.dto.request.PoolPreference;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/topic")
@Profile({"dev", "local"})
@Tag(name = "Kafka Message Schema", description = "Kafka 메시지 스키마를 문서화합니다.")
public class KafkaSchemaController {

	@Operation(summary = "topic/buddyservice.task.user-concept-preferences"
		, description = "buddyservice.task.user-pool-preferences 토픽으로 전달가능한 Queue 메시지를 제공합니다.\n"
		+ "사용자의 관심자 선호 최종 결과를 큐 메시지로 전달되어야 합니다. ")
	@GetMapping("/buddyservice.task.user-concept-preferences")
	public ConceptPreference getConceptPreferenceSchema() {
		return ConceptPreference.builder()
			.userId(12345L)
			.preferredConcepts(Set.of(BuddyEventConcept.FUN.name(), BuddyEventConcept.PHOTO.name()))
			.build();
	}

	@Operation(summary = "topic/buddyservice.task.user-pool-preferences"
		, description = "buddyservice.task.user-pool-preferences 토픽으로 전달가능한 Queue 메시지를 제공합니다.\n"
		+ "사용자의 관심자 선호 최종 결과를 큐 메시지로 전달되어야 합니다. ")
	@GetMapping("/buddyservice.task.user-pool-preferences")
	public PoolPreference getPoolPreferenceSchema() {
		return PoolPreference.builder()
			.userId(12345L)
			.preferredPools(Set.of(DivingPool.K26.name(), DivingPool.OLYMPIC_DIVING_POOL.name()))
			.build();
	}
}