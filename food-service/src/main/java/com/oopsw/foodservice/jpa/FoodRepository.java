package com.oopsw.foodservice.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
	int deleteByMemberIdAndFoodId(String memberId, String foodId);
	FoodEntity findByMemberIdAndFoodId(String memberId, String foodId);
	List<FoodEntity> findByMemberIdAndIntakeDate(String memberId, Date intakeDate);
}
