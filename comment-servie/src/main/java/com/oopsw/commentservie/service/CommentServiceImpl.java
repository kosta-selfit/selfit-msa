package com.oopsw.commentservie.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oopsw.commentservie.dto.CommentDto;
import com.oopsw.commentservie.jpa.CommentEntity;
import com.oopsw.commentservie.jpa.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<CommentDto> getComments(String boardId, int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdDate"));
		return commentRepository.findByBoardId(boardId, pageable)
			.stream()
			.map(commentEntity -> modelMapper.map(commentEntity, CommentDto.class))
			.toList();
	}

	@Override
	public void addComment(CommentDto commentDto) {
		CommentEntity commentEntity = modelMapper.map(commentDto, CommentEntity.class);
		commentEntity.setCommentId(UUID.randomUUID().toString());
		commentRepository.save(commentEntity);
	}
}
