package com.oopsw.foodservice.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsw.foodservice.dto.FoodDto;
import com.oopsw.foodservice.service.FoodService;
import com.oopsw.foodservice.vo.request.ReqAddFood;
import com.oopsw.foodservice.vo.request.ReqGetFood;
import com.oopsw.foodservice.vo.request.ReqRemoveFood;
import com.oopsw.foodservice.vo.response.ResGetFood;
import com.oopsw.foodservice.vo.response.ResMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-service")
public class FoodController {
	private final FoodService foodService;

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}

	@PostMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> addFood(@PathVariable("memberId") String memberId, @RequestBody ReqAddFood reqAddFood) {
		FoodDto foodDto = new ModelMapper().map(reqAddFood, FoodDto.class);
		foodDto.setMemberId(memberId);
		foodService.addFood(foodDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

	@DeleteMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> removeFood(@PathVariable("memberId") String memberId, @RequestBody ReqRemoveFood reqRemoveFood) {
		foodService.removeFood(FoodDto.builder().foodId(reqRemoveFood.getFoodId()).memberId(memberId).build());
		return ResponseEntity.ok(new ResMessage("success"));
	}

}
