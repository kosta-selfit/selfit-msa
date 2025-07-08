package com.oopsw.exerciseservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oopsw.exerciseservice.dto.ExerciseDto;
import com.oopsw.exerciseservice.jpa.ExerciseEntity;
import com.oopsw.exerciseservice.jpa.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

	private final ExerciseRepository exerciseRepository;

	@Override
	public boolean addExercise(ExerciseDto exerciseDto) {
		log.info("Adding exerciseDto " + exerciseDto.getExerciseName());
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
			.exerciseId(String.format("e%03d", count))
			.exerciseKcal(kcal)
			.build();

		// JPA 저장
		exerciseRepository.save(exerciseEntity);
		return true;
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


}
