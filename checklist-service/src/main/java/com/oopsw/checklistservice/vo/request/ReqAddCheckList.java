package com.oopsw.checklistservice.vo.request;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class ReqAddCheckList {
	private Integer isChecked;
	private Date checklistDate;
	private String checklistContent;
}
