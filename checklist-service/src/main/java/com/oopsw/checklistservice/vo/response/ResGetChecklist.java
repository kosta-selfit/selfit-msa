package com.oopsw.checklistservice.vo.response;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class ResGetChecklist {
	private String checklistContent;
	private Integer isChecked;
	private Date checklistDate;
}
