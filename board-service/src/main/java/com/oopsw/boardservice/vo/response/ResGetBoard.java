package com.oopsw.boardservice.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResGetBoard {
	private String boardTitle;
	private String categoryName;
	private String nickName;
	private String createdDate;
	private int commentCount;
	private int viewCount;

}
