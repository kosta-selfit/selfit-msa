package com.oopsw.boardservice.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsw.boardservice.dto.BoardDto;
import com.oopsw.boardservice.service.BoardService;
import com.oopsw.boardservice.vo.request.ReqAddBoard;
import com.oopsw.boardservice.vo.response.ResGetBoard;
import com.oopsw.boardservice.vo.response.ResGetCategorys;
import com.oopsw.boardservice.vo.response.ResMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board-service")
public class BoardController {

	private final Environment environment;
	private final ModelMapper modelMapper;
	private final BoardService boardService;

	@GetMapping("/rest_check")
	public Map<String, String> restCheck() {
		return Map.of("message", environment.getProperty("shop.message") + "Board check");
	}

	@GetMapping("/category")
	public ResponseEntity<List<ResGetCategorys>> getCategorys() {

		return null;
	}

	@PostMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> addBoard(@RequestBody ReqAddBoard reqAddBoard, @PathVariable String memberId){
		BoardDto boardDto = modelMapper.map(reqAddBoard, BoardDto.class);
		boardDto.setMemberId(memberId);
		log.info("잘나오나? "+boardDto);
		boardService.addBoard(boardDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

	@GetMapping("/{categoryId}/list/{sortOrder}/{keyword}")
	public ResponseEntity<List<ResGetBoard>> getBoards(@PathVariable int page,
													@PathVariable String categoryId,
													@PathVariable String sortOrder,
													@PathVariable String keyword){
		log.info("getBoards - page: {}, categoryId: {}, keyword: {}, sortOrder: {}", page, categoryId, keyword,
			sortOrder);

		List<BoardDto> boardDto = boardService.getBoards(page, categoryId, sortOrder, keyword);
		List<ResGetBoard> resGetBoard = boardDto.stream()
			.map(dto -> modelMapper.map(dto, ResGetBoard.class))
			.collect(Collectors.toList());
		log.info("getBoards - resp: {}", resGetBoard);
		return ResponseEntity.ok(resGetBoard);
	}


}
