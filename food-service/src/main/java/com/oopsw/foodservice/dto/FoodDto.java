package com.oopsw.foodservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDto {
	private String foodId;
	private Date intakeDate;
	private String foodName;
	private Float intake;
	private Float intakeKcal;
	private Integer unitKcal;
	private String memberId;
}
