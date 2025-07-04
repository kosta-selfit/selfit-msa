package com.oopsw.exerciseservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/exercise-service")
public class ExerciseController {
	@GetMapping("/api-test")
	public String test() {
		return "test";
	}
}
