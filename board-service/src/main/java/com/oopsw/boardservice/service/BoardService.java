package com.oopsw.boardservice.service;

import java.util.List;

import com.oopsw.boardservice.dto.BoardDto;

public interface BoardService {

	BoardDto getBoard(String boardId);

	List<BoardDto> getBoards(int page, String categoryName, String sortOrder, String keyword);

	void addBoard(BoardDto boardDto);

	void setBoard(BoardDto boardDto);

	// BoardDto getBoardTotal();
}
