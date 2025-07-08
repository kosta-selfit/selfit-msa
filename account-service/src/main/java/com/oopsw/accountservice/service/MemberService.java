package com.oopsw.accountservice.service;

import com.oopsw.accountservice.dto.MemberDto;
import com.oopsw.accountservice.vo.response.ResGetMember;

public interface MemberService {
	void addMember(MemberDto memberDto);

	MemberDto getMember(String memberId);

	void setMember(MemberDto memberDto);

	void removeMember(String memberId);

	Boolean checkEmail(MemberDto memberDto);

	Boolean checkNickname(MemberDto memberDto);

	Boolean checkPw(MemberDto memberDto);
}
