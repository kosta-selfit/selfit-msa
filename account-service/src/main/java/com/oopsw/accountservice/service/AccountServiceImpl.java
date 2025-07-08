package com.oopsw.accountservice.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oopsw.accountservice.dto.MemberDto;
import com.oopsw.accountservice.jpa.MemberEntity;
import com.oopsw.accountservice.jpa.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public void addMember(MemberDto memberDto) {
		memberDto.setMemberId(UUID.randomUUID().toString());
		memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

		memberRepository.save(new ModelMapper().map(memberDto, MemberEntity.class));
	}
}
