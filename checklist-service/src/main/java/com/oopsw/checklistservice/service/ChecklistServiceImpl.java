package com.oopsw.checklistservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsw.checklistservice.dto.ChecklistDto;
import com.oopsw.checklistservice.jpa.ChecklistEntity;
import com.oopsw.checklistservice.jpa.ChecklistRepository;
import com.oopsw.checklistservice.vo.response.ResGetChecklist;

@Service
public class ChecklistServiceImpl implements ChecklistService {
	private ChecklistRepository checklistRepository;

	@Autowired
	public ChecklistServiceImpl(ChecklistRepository checklistRepository) {
		this.checklistRepository = checklistRepository;
	}


	@Override
	public List<ChecklistDto> getChecklists(ChecklistDto checklistDto) {
		return List.of();
	}

	@Override
	public void setIsCheckItem(ChecklistDto checklistDto) {
		Optional<ChecklistEntity> optionalEntity = checklistRepository
			.findByChecklistIdAndMemberId(checklistDto.getChecklistId(), checklistDto.getMemberId());

		if (optionalEntity.isPresent()) {
			ChecklistEntity entity = optionalEntity.get();
			entity.setIsChecked(checklistDto.getIsChecked());
			checklistRepository.save(entity);
		} else {
			throw new IllegalArgumentException("해당 체크리스트 항목이 존재하지 않습니다.");
		}

	}

	@Override
	public void removeChecklist(ChecklistDto checklistDto) {
		Optional<ChecklistEntity> checklistEntity = checklistRepository.findByChecklistIdAndMemberId(checklistDto.getChecklistId(), checklistDto.getMemberId());
		if (checklistEntity.isPresent()) {
			checklistRepository.delete(checklistEntity.get());
		} else {
			throw new IllegalArgumentException("해당 체크리스트 항목이 존재하지 않습니다.");
		}

	}

	@Override
	public void addChecklist(ChecklistDto checklistDto) {
		int count = (checklistRepository.findAll().size())+1;
		checklistDto.setChecklistId(String.format("Ch%03d", count));
		checklistRepository.save(new ModelMapper().map(checklistDto, ChecklistEntity.class));
		checklistRepository.findById(1L);
	}
}

