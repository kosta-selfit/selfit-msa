package com.oopsw.accountservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oopsw.accountservice.service.AccountService;
import com.oopsw.accountservice.vo.request.ReqAddMember;
import com.oopsw.accountservice.vo.request.ReqCheckEmail;
import com.oopsw.accountservice.vo.request.ReqCheckNickname;
import com.oopsw.accountservice.vo.request.ReqCheckPw;
import com.oopsw.accountservice.vo.request.ReqSetMember;
import com.oopsw.accountservice.vo.response.ResGetMember;
import com.oopsw.accountservice.vo.response.ResMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-service")
public class AccountController {

	private final AccountService accountService;

	@GetMapping("/api-test")
	public String test() {
		return "test";
	}

	@GetMapping("/member/{memberId}")
	public ResponseEntity<ResGetMember> getMember(@PathVariable String memberId) {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/member")
	public ResponseEntity<ResMessage> addMember(@RequestBody ReqAddMember reqAddMember) {
		return ResponseEntity.ok(null);
	}

	@PutMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> setMember(@PathVariable String memberId, @RequestBody ReqSetMember reqSetMember) {
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/member/{memberId}")
	public ResponseEntity<ResMessage> removeMember(@PathVariable String memberId) {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/check-email")
	public ResponseEntity<ResMessage> checkEmail(@RequestBody ReqCheckEmail reqCheckEmail) {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/check-nickname")
	public ResponseEntity<ResMessage> checkNickname(@RequestBody ReqCheckNickname reqCheckNickname) {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/member/check-pw")
	public ResponseEntity<ResMessage> checkPw(@RequestBody ReqCheckPw reqCheckPw) {
		return ResponseEntity.ok(null);
	}

}
