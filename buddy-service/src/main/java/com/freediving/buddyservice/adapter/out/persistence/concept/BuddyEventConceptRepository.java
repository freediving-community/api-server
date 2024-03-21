package com.freediving.buddyservice.adapter.out.persistence.concept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.common.enumeration.EventConcept;

@Repository
public interface BuddyEventConceptRepository extends JpaRepository<EventConceptJpaEntity, EventConcept> {

	List<EventConceptJpaEntity> findByEnabledIsTrueOrderByDisplayOrderAsc();

}
