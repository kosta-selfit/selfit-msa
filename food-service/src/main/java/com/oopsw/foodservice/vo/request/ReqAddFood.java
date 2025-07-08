package com.oopsw.foodservice.vo.request;

import java.util.Date;

import lombok.Data;

@Data
public class ReqAddFood {
	private String foodId;
	private Date intakeDate;
	private String foodName;
	private Float intake;
	private Float intakeKcal;
	private Integer unitKcal;
}
