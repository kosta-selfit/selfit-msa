package com.oopsw.checklistservice.controller;

import java.util.List;

import org.hibernate.annotations.Check;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsw.checklistservice.dto.ChecklistDto;
import com.oopsw.checklistservice.service.ChecklistService;
import com.oopsw.checklistservice.vo.request.ReqAddCheckItem;
import com.oopsw.checklistservice.vo.request.ReqAddCheckList;
import com.oopsw.checklistservice.vo.request.ReqGetChecklist;
import com.oopsw.checklistservice.vo.request.ReqRemoveChecklist;
import com.oopsw.checklistservice.vo.request.ReqSetCheckItem;
import com.oopsw.checklistservice.vo.request.ReqSetIsCheckItem;
import com.oopsw.checklistservice.vo.response.ResGetChecklist;
import com.oopsw.checklistservice.vo.response.ResMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklist-service")
public class ChecklistController {
	private final ChecklistService checklistService;

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}

	@PostMapping("/{checklistId}/member/{memberId}")
	public ResponseEntity<List<ResGetChecklist>> getCheckList (@PathVariable String memberId, @RequestBody ReqGetChecklist reqGetChecklist) {

		return ResponseEntity.ok(null);
	}
	@PutMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> setCheckItem (@PathVariable String memberId, @RequestBody ReqSetCheckItem reqSetCheckItem) {

		return  ResponseEntity.ok(null);
	}
	@PutMapping("/item/checklist/member/{memberId}")
	public ResponseEntity<ResMessage> setIsCheckItem(@PathVariable String memberId, @RequestBody ReqSetIsCheckItem reqSetIsCheckItem) {
		ChecklistDto checklistDto = new ModelMapper().map(reqSetIsCheckItem, ChecklistDto.class);
		checklistDto.setMemberId(memberId);
		checklistService.setIsCheckItem(checklistDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}
	@DeleteMapping("/item/checklist/member/{memberId}")
	public ResponseEntity<ResMessage> removeChecklist (@PathVariable String memberId, @RequestBody ReqRemoveChecklist reqRemoveChecklist) {
		ChecklistDto checklistDto = ChecklistDto.builder().
			checklistId(reqRemoveChecklist.getChecklistId()).
			memberId(memberId).
			build();
		checklistService.removeChecklist(checklistDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}
	@PostMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> addChecklist (@PathVariable String memberId, @RequestBody ReqAddCheckList reqAddCheckList) {
		ChecklistDto checkListDto = new ModelMapper().map(reqAddCheckList, ChecklistDto.class);
		checkListDto.setMemberId(memberId);
		checklistService.addChecklist(checkListDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

}
