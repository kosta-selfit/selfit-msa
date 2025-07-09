package com.oopsw.foodservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsw.foodservice.dto.FoodDto;
import com.oopsw.foodservice.jpa.FoodEntity;
import com.oopsw.foodservice.jpa.FoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
	private final FoodRepository foodRepository;

	@Override
	public FoodDto addFood(FoodDto foodDto) {
		FoodEntity foodEntity = new ModelMapper().map(foodDto, FoodEntity.class);
		// 1. foodId 자동 증가
		foodEntity.setFoodId(String.format("f%04d", (foodRepository.findAll().size())+1));
		// 2. 섭취량과 단위 계산해서 섭취 칼로리 계산
		foodEntity.setIntakeKcal(foodDto.getIntake()/100f*foodDto.getUnitKcal());
		// 3. 저장
		foodRepository.save(foodEntity);
		return foodDto;
	}

	@Override
	@Transactional
	public FoodDto setFood(FoodDto foodDto) {
		FoodEntity foodEntity = foodRepository.findByMemberIdAndFoodId(foodDto.getMemberId(), foodDto.getFoodId());

		if(foodEntity == null) {
			throw new IllegalArgumentException("Invalid foodId");
		}

		foodEntity.setIntake(foodDto.getIntake());
		foodEntity.setIntakeKcal(foodDto.getIntake()/100f*foodEntity.getUnitKcal());
		foodRepository.save(foodEntity);
		return foodDto;
	}

	@Override
	@Transactional
	public void removeFood(FoodDto foodDto) {
		int foodEntity = foodRepository.deleteByMemberIdAndFoodId(foodDto.getMemberId(), foodDto.getFoodId());

		if(foodEntity == 0) {
			throw new IllegalArgumentException("Invalid memberId or foodId");
		}
	}
}
