package com.oopsw.foodservice.service;

import com.oopsw.foodservice.dto.FoodDto;

public interface FoodService {
	FoodDto addFood(FoodDto foodDto);
	void removeFood(FoodDto foodDto);

}
