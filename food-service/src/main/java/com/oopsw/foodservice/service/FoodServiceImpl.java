package com.oopsw.foodservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsw.foodservice.dto.FoodApiDto;
import com.oopsw.foodservice.dto.FoodDto;
import com.oopsw.foodservice.jpa.FoodEntity;
import com.oopsw.foodservice.repository.FoodApiRepository;
import com.oopsw.foodservice.repository.FoodRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
	private final FoodRepository foodRepository;
	private final FoodApiRepository foodApiRepository;
	private final ModelMapper modelMapper;

	@Override
	public Mono<List<FoodApiDto>> getFoodByNameLike(FoodApiDto foodApiDto) {
		if(foodApiDto.getKeyword() == null || foodApiDto.getKeyword().isBlank()) {
			return Mono.error(new IllegalArgumentException("검색 키워드를 입력해야 합니다."));
		}
		if(foodApiDto.getPageNo() < 1 || foodApiDto.getNumOfRows() < 1) {
			return Mono.error(new IllegalArgumentException("Invalid pageNo or numOfRows"));
		}
		return foodApiRepository.fetchFoodData(foodApiDto.getPageNo(), foodApiDto.getNumOfRows())
			.map(list ->
				// Java 스트림을 이용해 'foodNm'에 keyword 포함된 항목만 필터
				list.stream()
					.filter(item -> {
						// null 검사 추가
						String name = item.getFoodNm();
						return name != null && name.contains(foodApiDto.getKeyword());
					})
					.collect(Collectors.toList())
			);
	}

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
		// foodEntity.setFoodId(String.format("f%04d", (foodRepository.findAll().size())+1));
		foodEntity.setFoodId(UUID.randomUUID().toString());
		foodEntity.setIntakeKcal(foodDto.getIntake()/100f*foodDto.getUnitKcal());
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
