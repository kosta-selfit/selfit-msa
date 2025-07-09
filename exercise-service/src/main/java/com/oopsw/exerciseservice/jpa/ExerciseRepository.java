package com.oopsw.exerciseservice.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
	List<ExerciseEntity> findByExerciseDate(String exerciseDate);

	List<ExerciseEntity> findByExerciseDateAndMemberId(String exerciseDate, String memberId);
	boolean existsByMemberIdAndExerciseId(String memberId, String exerciseId);
	Integer deleteByMemberIdAndExerciseId(String memberId, String exerciseId);
	ExerciseEntity findByExerciseId(String exerciseId);
	List<ExerciseEntity> findByMemberIdAndExerciseDateBetween(String memberId, String startDate, String endDate);

}
