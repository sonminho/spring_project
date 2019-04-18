package edu.autocar.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.autocar.domain.LoginInfo;
import edu.autocar.domain.Member;
import edu.autocar.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	@Autowired
	MemberService service;

	@GetMapping("/login")
	public String login(LoginInfo loginInfo, @ModelAttribute("target") String target,
			@ModelAttribute("reason") String reason) {
		loginInfo.setTarget(target);
		loginInfo.setReason(reason);
		return "member/login";
	}

	@PostMapping("/login")
	public String postLogin(@Valid LoginInfo loginInfo, BindingResult result, HttpSession session) throws Exception {
		if (result.hasErrors())
			return "member/login";
		Member member = service.checkPassword(loginInfo.getUserId(), loginInfo.getPassword());
		if (member != null) {
			session.setAttribute("USER", member);
			if (member.isAdmin()) {
				// 관리자 인경우
				System.out.println("관리자 로그인 성공");
				return "redirect:/admin/member/list";
			} else { // 일반 회원인 경우
				System.out.println("로그인 성공");
				return "redirect:/"; 
				} 
		} else { 
			System.out.println("사용자 ID 또는 비밀번호가 일치하지 않습니다.");
			result.reject("fail", "사용자 ID 또는 비밀번호가 일치하지 않습니다.");
			return "member/login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}