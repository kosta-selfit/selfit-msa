package com.oopsw.commentservie.vo.response;

import java.util.Date;

import lombok.Data;

@Data
public class ResGetComments {
	private String commentContent;
	private Date commentCreatedDate;
	private String memberId;
}
