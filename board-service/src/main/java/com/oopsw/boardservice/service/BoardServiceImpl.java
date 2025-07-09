package com.oopsw.boardservice.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oopsw.boardservice.dto.BoardDto;
import com.oopsw.boardservice.jpa.BoardEntity;
import com.oopsw.boardservice.jpa.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<BoardDto> getBoards(int page, String categoryId, String sortOrder, String keyword) {

		return List.of();
	}

	@Override
	public void addBoard(BoardDto boardDto) {
		String lastId = boardRepository.findTopByOrderByBoardIdDesc()
			.map(BoardEntity::getBoardId).orElse("b0000");

		int seq = Integer.parseInt(lastId.substring(1)) + 1;

		String newBoardId = String.format("b%04d", seq);

		boardDto.setBoardId(newBoardId);
		BoardEntity entity = modelMapper.map(boardDto, BoardEntity.class);
		boardRepository.save(entity);
	}
}
