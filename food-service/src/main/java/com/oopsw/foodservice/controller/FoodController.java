package com.oopsw.foodservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsw.foodservice.dto.FoodDto;
import com.oopsw.foodservice.service.FoodService;
import com.oopsw.foodservice.vo.request.ReqAddFood;
import com.oopsw.foodservice.vo.request.ReqGetFood;
import com.oopsw.foodservice.vo.request.ReqGetIntakeKcal;
import com.oopsw.foodservice.vo.request.ReqRemoveFood;
import com.oopsw.foodservice.vo.request.ReqSetFood;
import com.oopsw.foodservice.vo.response.ResGetFood;
import com.oopsw.foodservice.vo.response.ResGetIntakeKcal;
import com.oopsw.foodservice.vo.response.ResMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-service")
public class FoodController {
	private final FoodService foodService;
	private final ModelMapper modelMapper;

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}

	@PostMapping("/kcal/member/{memberId}")
	public ResponseEntity<ResGetIntakeKcal> getIntakeKcal(@PathVariable String memberId, @RequestBody ReqGetIntakeKcal reqGetIntakeKcal) {
		FoodDto foodDto = modelMapper.map(reqGetIntakeKcal, FoodDto.class);
		foodDto.setMemberId(memberId);
		ResGetIntakeKcal result = modelMapper.map(foodService.getIntakeKcal(foodDto), ResGetIntakeKcal.class);
		return ResponseEntity.ok(result);
	}


	@PostMapping("/foods/member/{memberId}")
	public ResponseEntity<List<ResGetFood>> getFood(@PathVariable("memberId") String memberId, @RequestBody ReqGetFood reqGetFood) {
		FoodDto foodDto = modelMapper.map(reqGetFood, FoodDto.class);
		foodDto.setMemberId(memberId);

		List<FoodDto> dtoList = foodService.getFood(foodDto);

		List<ResGetFood> result = new ArrayList<>();
		for (FoodDto dto : dtoList) {
			result.add(modelMapper.map(dto, ResGetFood.class));
		}
		return ResponseEntity.ok(result);

	}

	@PostMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> addFood(@PathVariable("memberId") String memberId, @RequestBody ReqAddFood reqAddFood) {
		FoodDto foodDto = modelMapper.map(reqAddFood, FoodDto.class);
		foodDto.setMemberId(memberId);
		foodService.addFood(foodDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

	@PutMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> setFood(@PathVariable("memberId") String memberId,@RequestBody ReqSetFood reqSetFood) {
		FoodDto foodDto = modelMapper.map(reqSetFood, FoodDto.class);
		foodDto.setMemberId(memberId);
		foodService.setFood(foodDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

	@DeleteMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> removeFood(@PathVariable("memberId") String memberId, @RequestBody ReqRemoveFood reqRemoveFood) {
		FoodDto foodDto = modelMapper.map(reqRemoveFood, FoodDto.class);
		foodDto.setMemberId(memberId);
		foodService.removeFood(foodDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

}
