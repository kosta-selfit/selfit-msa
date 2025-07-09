package com.oopsw.commentservie.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsw.commentservie.dto.CommentDto;
import com.oopsw.commentservie.service.CommentService;
import com.oopsw.commentservie.vo.request.ReqAddComment;
import com.oopsw.commentservie.vo.response.ResGetComments;
import com.oopsw.commentservie.vo.response.ResMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment-service")
public class CommentController {

	private final CommentService commentService;
	private final ModelMapper modelMapper;

	@GetMapping("/board/{boardId}/comments/{page}")
	public ResponseEntity<List<ResGetComments>> getComments(@PathVariable String boardId, @PathVariable int page) {
		List<ResGetComments> comments = commentService.getComments(boardId, page)
			.stream()
			.map(commentDto -> modelMapper.map(commentDto, ResGetComments.class))
			.toList();
		return ResponseEntity.ok(comments);
	}

	@PostMapping("/board/{boardId}/comment")
	public ResponseEntity<ResMessage> addComment(@PathVariable String boardId, @RequestBody ReqAddComment reqAddComment) {
		CommentDto commentDto = modelMapper.map(reqAddComment, CommentDto.class);
		commentDto.setBoardId(boardId);
		commentService.addComment(commentDto);
		return ResponseEntity.ok(new ResMessage("success"));
	}

}
