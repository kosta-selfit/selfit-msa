package com.oopsw.checklistservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklist-service")
public class ChecklistController {

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}

}
