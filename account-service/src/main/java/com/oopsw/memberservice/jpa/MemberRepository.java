package com.oopsw.memberservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	MemberEntity findByMemberId(String memberId);
	MemberEntity findByEmail(String email);
	MemberEntity findByNickname(String nickname);
	void deleteByMemberId(String memberId);
}
