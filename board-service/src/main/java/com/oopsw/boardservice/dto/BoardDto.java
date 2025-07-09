package com.oopsw.boardservice.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
	private Long id;
	private String boardTitle;
	private String boardContent;
	private String categoryId;
	private String categoryName;
	private String boardImg;
	private String createdDate;
	private String boardId;
	private String memberId;
}
