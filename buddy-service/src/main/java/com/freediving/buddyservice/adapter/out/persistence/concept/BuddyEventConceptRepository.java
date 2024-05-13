package com.freediving.buddyservice.adapter.out.persistence.concept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freediving.buddyservice.domain.enumeration.BuddyEventConcept;

@Repository
public interface BuddyEventConceptRepository extends JpaRepository<BuddyEventConceptJpaEntity, BuddyEventConcept> {

	List<BuddyEventConceptJpaEntity> findByEnabledIsTrueOrderByDisplayOrderAsc();

}
