package com.oopsw.memberservice.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oopsw.memberservice.dto.MemberDto;
import com.oopsw.memberservice.jpa.MemberEntity;
import com.oopsw.memberservice.jpa.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public void addMember(MemberDto memberDto) {
		memberDto.setMemberId(UUID.randomUUID().toString());
		memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));
		memberDto.setBmr(calculateBmr(memberDto));

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
	public MemberDto getMemberByEmail(String email) {
		MemberEntity memberEntity = memberRepository.findByEmail(email);
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
		memberDto.setBmr(calculateBmr(memberDto));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(memberDto, memberEntity);
		memberEntity.setId(id);
	}

	@Override
	@Transactional
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
		return passwordEncoder.matches(memberDto.getPw(), memberEntity.getPw());
	}

	private Float calculateBmr(MemberDto memberDto) {
		Float height = memberDto.getHeight();
		Float weight = memberDto.getWeight();
		Integer age = getAge(memberDto);
		String gender = memberDto.getGender();

		if (height == null || weight == null || age == null || gender == null) {
			return null;
		}

		Double bmr;
		if (gender.equals("남자")) {
			bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
		} else {
			bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
		}

		return bmr.floatValue();
	}

	private Integer getAge(MemberDto memberDto) {
		LocalDate today = LocalDate.now();
		Date birthday = memberDto.getBirthday();

		if (birthday == null) {
			return null;
		}

		LocalDate birthdateLocal = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return Period.between(birthdateLocal, today).getYears();
	}

}
