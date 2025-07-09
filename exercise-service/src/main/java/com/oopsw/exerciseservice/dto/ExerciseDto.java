package com.oopsw.exerciseservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExerciseDto {
	private Long id;
	private String memberId;
	private String exerciseId;
	private String exerciseDate;
	private String exerciseName;
	private int exerciseMin;
	private float exerciseKcal;
	private float met;
	private String keyword;
	private int pageNo;
	private int numOfRows;
	private int newMin;

}
