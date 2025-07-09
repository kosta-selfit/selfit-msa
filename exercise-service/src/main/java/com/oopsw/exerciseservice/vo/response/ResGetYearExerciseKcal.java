package com.oopsw.exerciseservice.vo.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResGetYearExerciseKcal {
	private String exerciseDate;
	private float exerciseSum;
}
