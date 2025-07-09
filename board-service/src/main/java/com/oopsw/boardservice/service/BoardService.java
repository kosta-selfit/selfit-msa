package com.oopsw.boardservice.service;

import java.util.List;

import com.oopsw.boardservice.dto.BoardDto;

public interface BoardService {

	List<BoardDto> getBoards(int page, String categoryId, String sortOrder, String keyword);

	void addBoard(BoardDto boardDto);
}
