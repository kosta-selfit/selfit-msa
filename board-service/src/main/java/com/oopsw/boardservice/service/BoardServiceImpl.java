package com.oopsw.boardservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oopsw.boardservice.dto.BoardDto;
import com.oopsw.boardservice.jpa.BoardEntity;
import com.oopsw.boardservice.jpa.BoardRepository;
import com.oopsw.boardservice.jpa.BookmarkEntity;
import com.oopsw.boardservice.jpa.BookmarkRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final BookmarkRepository bookmarkRepository;
	private final ModelMapper modelMapper;

	private void validateBoardRequiredFields(BoardDto boardDto) {
		if (boardDto.getBoardTitle() == null || boardDto.getBoardTitle().isBlank()) {
			throw new IllegalArgumentException("제목은 필수입니다.");
		}
		if (boardDto.getBoardContent() == null || boardDto.getBoardContent().isBlank()) {
			throw new IllegalArgumentException("내용은 필수입니다.");
		}
		if (boardDto.getCategoryName() == null) {
			throw new IllegalArgumentException("카테고리를 선택해주세요.");
		}
	}

	@Override
	public BoardDto getBoard(BoardDto boardDto) {
		BoardEntity boardEntity = boardRepository.findByBoardId(boardDto.getBoardId())
			.orElseThrow(() ->
				new IllegalArgumentException("게시글이 존재하지 않습니다.")
			);
		boardEntity.setViewCount(boardEntity.getViewCount() + 1);
		boardRepository.save(boardEntity);

		BoardDto resultBoard = modelMapper.map(boardEntity, BoardDto.class);
		return resultBoard;
	}

	@Override
	public List<BoardDto> getBoards(int page, String categoryName, String sortOrder, String keyword) {
		int pageSize = 5;

		Sort.Direction dir = "asc".equalsIgnoreCase(sortOrder)
			? Sort.Direction.ASC
			: Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by(dir, "createdDate"));

		Page<BoardEntity> pageResult;

		if(keyword != null && !keyword.isEmpty()) {
			pageResult = boardRepository.findByCategoryNameAndBoardTitleContainingIgnoreCaseOrCategoryNameAndBoardContentContainingIgnoreCase(
				categoryName, keyword,
				categoryName, keyword,
				pageable
			);
		} else {
			pageResult = boardRepository.findAllByCategoryName(categoryName, pageable);
		}

		int totalBoards = (int)pageResult.getTotalElements();

		if(pageResult.isEmpty()){
			return Collections.emptyList();
		}
		List<BoardDto> boardDtoList = pageResult.stream().map(
			entity -> {
				BoardDto boardDto = modelMapper
					.map(entity, BoardDto.class);
				boardDto.setTotalCount(totalBoards);
				return boardDto;
			})
			.collect(Collectors.toList());
		return boardDtoList;
	}

	// @Override
	// public BoardDto getBoardTotal() {
	//
	// 	BoardDto boardDto = boardRepository.find
	//
	// 	return null;
	// }

	@Override
	public void addBoard(BoardDto boardDto) {
		validateBoardRequiredFields(boardDto);

		String lastId = boardRepository.findTopByOrderByBoardIdDesc()
			.map(BoardEntity::getBoardId).orElse("b0000");

		int seq = Integer.parseInt(lastId.substring(1)) + 1;

		String newBoardId = String.format("b%04d", seq);

		boardDto.setBoardId(newBoardId);
		BoardEntity entity = modelMapper.map(boardDto, BoardEntity.class);
		boardRepository.save(entity);
	}

	@Override
	public void setBoard(BoardDto boardDto) {
		validateBoardRequiredFields(boardDto);

		BoardEntity boardEntity = boardRepository.findByBoardId(boardDto.getBoardId())
				.orElseThrow(() ->
						new IllegalArgumentException("수정하려는 게시글이 존재하지 않습니다."));
		boardEntity.setCategoryName(boardDto.getCategoryName());
		boardEntity.setBoardTitle(boardDto.getBoardTitle());
		boardEntity.setBoardContent(boardDto.getBoardContent());
		System.out.println(boardEntity);
		boardRepository.save(boardEntity);
	}

	@Override
	public void removeBoard(BoardDto boardDto) {
		System.out.println(boardDto);
		BoardEntity boardEntity = boardRepository.findByBoardId(boardDto.getBoardId())
			.orElseThrow(()->
				new IllegalArgumentException("삭제하려는 게시글이 존재하지 않습니다."));

		if (boardEntity.getBoardId().equals(boardDto.getBoardId())) {
			boardRepository.delete(boardEntity);
		}
	}

	@Override
	public void toggleBookmark(BoardDto boardDto) {
		if (boardDto.getMemberId() == null) {
			throw new IllegalArgumentException("로그인을 해주세요.");
		}

		BookmarkEntity bookmarkEntity = bookmarkRepository
			.findByBoardIdAndMemberId(boardDto.getBoardId(), boardDto.getMemberId());

		if (bookmarkEntity != null) {
			bookmarkRepository.delete(bookmarkEntity);
		} else {
			bookmarkRepository.save(modelMapper.map(boardDto, BookmarkEntity.class));
		}
	}
}
