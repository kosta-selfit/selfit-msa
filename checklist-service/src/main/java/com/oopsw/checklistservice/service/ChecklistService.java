package com.oopsw.checklistservice.service;

import java.util.List;

import com.oopsw.checklistservice.dto.ChecklistDto;
import com.oopsw.checklistservice.vo.response.ResGetChecklist;

public interface ChecklistService {

	List<ChecklistDto> getChecklists(ChecklistDto checklistDto);
	void addChecklist(ChecklistDto checklistDto);
	void removeChecklist(ChecklistDto checklistDto);
	void setIsCheckItem(ChecklistDto checklistDto);


}
