package com.oopsw.exerciseservice.vo.request;

import java.util.Date;

import lombok.Data;

@Data
public class ReqAddExercise {
	private String exerciseDate;
	private String memberId;
	private String exerciseName;
	private int exerciseMin;
	private float met;
}
