package com.oopsw.exerciseservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsw.exerciseservice.dto.ExerciseDto;
import com.oopsw.exerciseservice.service.ExerciseService;
import com.oopsw.exerciseservice.vo.request.ReqAddExercise;
import com.oopsw.exerciseservice.vo.request.ReqGetExerciseApi;
import com.oopsw.exerciseservice.vo.request.ReqGetExercises;
import com.oopsw.exerciseservice.vo.request.ReqRemoveExercise;
import com.oopsw.exerciseservice.vo.request.ReqSetExerciseMin;
import com.oopsw.exerciseservice.vo.response.ResGetExerciseApi;
import com.oopsw.exerciseservice.vo.response.ResGetExercises;
import com.oopsw.exerciseservice.vo.response.ResMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/exercise-service")
public class ExerciseController {
	private final ExerciseService exerciseService;

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}

	@PostMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> addExercise(@RequestBody ReqAddExercise reqAddExercise, @PathVariable String memberId) {
		ExerciseDto exerciseDto = ExerciseDto.builder()
			.exerciseDate(reqAddExercise.getExerciseDate())
			.exerciseMin(reqAddExercise.getExerciseMin())
			.met(reqAddExercise.getMet())
			.exerciseName(reqAddExercise.getExerciseName())
			.memberId(memberId).build();
		exerciseService.addExercise(exerciseDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

	@PostMapping("/member/{memberId}")
	public ResponseEntity<List<ResGetExercises>> getExercises(@RequestBody ReqGetExercises reqGetExercises, @PathVariable String memberId) {
		ExerciseDto exerciseDto = ExerciseDto.builder()
			.exerciseDate(reqGetExercises.getExerciseDate())
			.memberId(memberId)
			.build();
		List<ExerciseDto> exerciseDtos = exerciseService.getExercises(exerciseDto);
		List<ResGetExercises> resGetExercises = new ArrayList<>();
		for (ExerciseDto exerciseDto1 : exerciseDtos) {
			ResGetExercises resGetExercise = ResGetExercises.builder()
				.exerciseDate(exerciseDto1.getExerciseDate())
				.exerciseKcal(exerciseDto1.getExerciseKcal())
				.exerciseName(exerciseDto1.getExerciseName())
				.exerciseMin(exerciseDto1.getExerciseMin())
				.build();
			resGetExercises.add(resGetExercise);
		}
		return ResponseEntity.ok(resGetExercises);
	}

	@DeleteMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> removeExercise(@PathVariable String exerciseId, @RequestBody ReqRemoveExercise reqRemoveExercise) {
		ExerciseDto exerciseDto = ExerciseDto.builder()
			.memberId(reqRemoveExercise.getExerciseId())
			.exerciseId(exerciseId)
			.build();
		exerciseService.removeExercise(exerciseDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

	@PutMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> setExerciseMin(@PathVariable String memberId, @RequestBody ReqSetExerciseMin reqSetExerciseMin) {
		ExerciseDto exerciseDto = ExerciseDto.builder()
			.exerciseId(reqSetExerciseMin.getExerciseId())
			.memberId(memberId)
			.newMin(reqSetExerciseMin.getNewMin())
			.build();
		exerciseService.setExerciseMin(exerciseDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}
}
