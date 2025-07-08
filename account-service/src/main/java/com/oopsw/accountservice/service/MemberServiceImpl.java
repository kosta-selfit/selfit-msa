package com.oopsw.accountservice.service;

import java.util.UUID;

import org.hibernate.validator.constraintvalidators.RegexpURLValidator;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsw.accountservice.dto.MemberDto;
import com.oopsw.accountservice.jpa.MemberEntity;
import com.oopsw.accountservice.jpa.MemberRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final EntityManager entityManager;
	private final RegexpURLValidator regexpURLValidator;

	@Override
	public void addMember(MemberDto memberDto) {
		memberDto.setMemberId(UUID.randomUUID().toString());
		memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

		memberRepository.save(new ModelMapper().map(memberDto, MemberEntity.class));
	}

	@Override
	public MemberDto getMember(String memberId) {
		MemberEntity memberEntity = memberRepository.findByMemberId(memberId);
		if (memberEntity == null) {
			throw new NullPointerException("Member not found");
		}
		return new ModelMapper().map(memberEntity, MemberDto.class);
	}

	@Override
	@Transactional
	public void setMember(MemberDto memberDto) {
		MemberEntity memberEntity = memberRepository.findByMemberId(memberDto.getMemberId());
		if (memberEntity == null) {
			throw new NullPointerException("Member not found");
		}

		Long id = memberEntity.getId();
		if (memberDto.getPw() != null) {
			memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));
		}

		new ModelMapper().map(memberDto, memberEntity);
		memberEntity.setId(id);
	}

	@Override
	public void removeMember(String memberId) {
		memberRepository.deleteByMemberId(memberId);
	}

	@Override
	public Boolean checkEmail(MemberDto memberDto) {
		return memberRepository.findByEmail(memberDto.getEmail()) != null;
	}

	@Override
	public Boolean checkNickname(MemberDto memberDto) {
		return memberRepository.findByNickname(memberDto.getNickname()) != null;
	}

	@Override
	public Boolean checkPw(MemberDto memberDto) {
		MemberEntity memberEntity = memberRepository.findByMemberId(memberDto.getMemberId());
		return memberEntity.getPw().equals(passwordEncoder.encode(memberDto.getPw()));
	}

}
