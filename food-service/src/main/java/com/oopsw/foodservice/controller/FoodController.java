package com.oopsw.foodservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-service")
public class FoodController {

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}

}
