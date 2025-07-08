package com.oopsw.exerciseservice.vo.request;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReqGetExercises {
	private String exerciseDate;
}
