package com.oopsw.exerciseservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oopsw.exerciseservice.dto.ExerciseDto;
import com.oopsw.exerciseservice.jpa.ExerciseEntity;
import com.oopsw.exerciseservice.jpa.ExerciseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;

	@Override
	public void addExercise(ExerciseDto exerciseDto) {
		// 소모 칼로리 계산(Member에서 Weight 가져와야 함.
		float kcal = 70  //dashboardRepository.getWeight(exercise.getExerciseNoteId())
			* exerciseDto.getMet()
			* exerciseDto.getExerciseMin() / 60f;


		int count = (exerciseRepository.findAll().size())+1;
		// DTO → Entity 매핑
		ExerciseEntity exerciseEntity = ExerciseEntity.builder()
			.exerciseMin(exerciseDto.getExerciseMin())
			// .exerciseKcal(kcal)
			.exerciseName(exerciseDto.getExerciseName())
			.met(exerciseDto.getMet())
			.memberId(exerciseDto.getMemberId())
			.exerciseDate(exerciseDto.getExerciseDate())
			.exerciseId(String.format("e%04d", count))
			.exerciseKcal(kcal)
			.build();

		// JPA 저장
		exerciseRepository.save(exerciseEntity);
	}

	@Override
	public List<ExerciseDto> getExercises(ExerciseDto exerciseDto) {
		ExerciseEntity exerciseEntity = ExerciseEntity.builder()
			.exerciseDate(exerciseDto.getExerciseDate())
			.memberId(exerciseDto.getMemberId())
			.build();
		List<ExerciseEntity> exerciseEntities = exerciseRepository.findByExerciseDateAndMemberId(exerciseEntity.getExerciseDate(), exerciseEntity.getMemberId());
		List<ExerciseDto> exerciseDtos = new ArrayList<>();
		for (ExerciseEntity exerciseEntity1 : exerciseEntities) {
			ExerciseDto dto = ExerciseDto.builder()
				.exerciseName(exerciseEntity1.getExerciseName())
				.exerciseDate(exerciseEntity1.getExerciseDate())
				.exerciseMin(exerciseEntity1.getExerciseMin())
				.exerciseKcal(exerciseEntity1.getExerciseKcal())
				.build();
			exerciseDtos.add(dto);
		}
		return exerciseDtos;
	}

	@Transactional
	public void removeExercise(ExerciseDto exerciseDto) {
		if (exerciseRepository.existsByMemberIdAndExerciseId(exerciseDto.getMemberId(), exerciseDto.getExerciseId())) {
			exerciseRepository.deleteByMemberIdAndExerciseId(exerciseDto.getMemberId(), exerciseDto.getExerciseId());
		}
	}


	public void setExerciseMin(ExerciseDto exerciseDto) {
		ExerciseEntity exerciseEntity = ExerciseEntity.builder()
			.memberId(exerciseDto.getMemberId())
			.exerciseId(exerciseDto.getExerciseId())
			.build();
		ExerciseEntity exercise = exerciseRepository.findByExerciseId(exerciseEntity.getExerciseId());

		exercise.setExerciseMin(exerciseDto.getNewMin());

		float weight = 70.0f; // member weight
		float met = exercise.getMet();
		float kcal = weight * met * exerciseDto.getNewMin() / 60f;

		exercise.setExerciseKcal(kcal);
		exerciseRepository.save(exercise);
	}
}
