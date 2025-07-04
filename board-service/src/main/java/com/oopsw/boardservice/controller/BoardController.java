package com.oopsw.boardservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board-service")
public class BoardController {

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}
}
