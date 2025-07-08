package com.oopsw.exerciseservice.vo.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResGetExerciseApi {
	private float met;
	private String exerciseName;
}
