package com.oopsw.exerciseservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oopsw.exerciseservice.dto.ExerciseDto;


public interface ExerciseService {
	boolean addExercise(ExerciseDto exerciseDto);
	List<ExerciseDto> getExercises(ExerciseDto exerciseDto);
}
