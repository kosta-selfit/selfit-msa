package com.oopsw.exerciseservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("운동명")
	private String exerciseName;
	private int exerciseMin;
	private float exerciseKcal;
	@JsonProperty("단위체중당에너지소비량")
	private float met;
	private float exerciseSum;
	private String year;
	private int newMin;

	private int pageNo;
	private int numOfRows;
	private String keyword;

}
