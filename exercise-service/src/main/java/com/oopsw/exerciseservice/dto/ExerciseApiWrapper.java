package com.oopsw.exerciseservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseApiWrapper {
	private int currentCount;
	private List<ExerciseDto> data;
	private int matchCount;
	private int page;
	private int perPage;
	private int totalCount;
}
