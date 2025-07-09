package com.oopsw.checklistservice.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<ChecklistEntity, Long> {
	List<ChecklistEntity> findByMemberId(String memberId);

	Optional<ChecklistEntity> findByChecklistIdAndMemberId(String checklistId, String memberId);

}
