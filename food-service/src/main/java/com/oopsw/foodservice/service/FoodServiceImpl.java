package com.oopsw.foodservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final ModelMapper modelMapper;

	@Override
	public FoodDto getIntakeKcal(FoodDto foodDto) {
		List<FoodEntity> foodEntities = foodRepository.findByMemberIdAndIntakeDate(foodDto.getMemberId(), foodDto.getIntakeDate());
		float intakeKcalSum = 0f;
		for (FoodEntity foodEntity : foodEntities) {
			if(foodEntity.getIntakeKcal() != null){
				intakeKcalSum += foodEntity.getIntakeKcal();
			}
		}
		return FoodDto.builder().intakeKcalSum(intakeKcalSum).build();
	}

	@Override
	public List<FoodDto> getYearIntakeKcal(FoodDto foodDto) {
		int year = foodDto.getYear();
		String memberId = foodDto.getMemberId();

		// 가져온 년도로 범위 설정
		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = LocalDate.of(year, 12, 31);

		List<FoodEntity> foodEntities = foodRepository.findByMemberIdAndIntakeDateBetween(memberId, java.sql.Date.valueOf(start),java.sql.Date.valueOf(end));

		// 날짜별 IntakeKcal 합계
		Map<Date, Float> IntakeKcalSumMap = new HashMap<>();
		for (FoodEntity foodEntity : foodEntities) {
			Date intakeDate = foodEntity.getIntakeDate();
			float kcal = foodEntity.getIntakeKcal() != null ? foodEntity.getIntakeKcal() : 0f;
			IntakeKcalSumMap.put(intakeDate, IntakeKcalSumMap.getOrDefault(intakeDate, 0f) + kcal);
		}

		List<FoodDto> foodDtoList = new ArrayList<>();
		for (Map.Entry<Date, Float> entry : IntakeKcalSumMap.entrySet()) {
			foodDtoList.add(FoodDto.builder()
				.intakeDate(entry.getKey())
				.intakeKcalSum(entry.getValue())
				.build());
		}

		// 날짜순 정렬
		foodDtoList.sort(Comparator.comparing(FoodDto::getIntakeDate));

		return foodDtoList;
	}

	@Override
	public List<FoodDto> getFood(FoodDto foodDto) {
		List<FoodEntity> foodEntities = foodRepository.findByMemberIdAndIntakeDate(foodDto.getMemberId(), foodDto.getIntakeDate());
		List<FoodDto> foodDtoList = new ArrayList<>();
		for (FoodEntity foodEntity : foodEntities) {
			foodDtoList.add(modelMapper.map(foodEntity, FoodDto.class));
		}
		return foodDtoList;
	}

	@Override
	public FoodDto addFood(FoodDto foodDto) {
		FoodEntity foodEntity = modelMapper.map(foodDto, FoodEntity.class);
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
