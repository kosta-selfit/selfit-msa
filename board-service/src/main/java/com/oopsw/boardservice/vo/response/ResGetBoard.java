package com.oopsw.boardservice.vo.response;

import java.util.Date;

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
	private Date createdDate;
	private int commentCount;
	private int viewCount;

}
