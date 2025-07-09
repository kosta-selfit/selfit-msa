package com.oopsw.memberservice.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDto {
	private Long id;
	private String memberId;
	private String email;
	private String pw;
	private String name;
	private String nickname;
	private String gender;
	private Date birthday;
	private Float height;
	private Float weight;
	private String goal;
	private Date joinDate;
	private String memberType;
	private String profileImg;
}
