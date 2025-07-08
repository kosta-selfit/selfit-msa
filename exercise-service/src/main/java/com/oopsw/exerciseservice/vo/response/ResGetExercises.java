package com.oopsw.exerciseservice.vo.response;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResGetExercises {
	private String exerciseDate;
	private String exerciseName;
	private int exerciseMin;
	private float exerciseKcal;
}
