package edu.autocar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.domain.Member;
import edu.autocar.domain.PageInfo;
import edu.autocar.domain.ResultMsg;
import edu.autocar.domain.UserLevel;
import edu.autocar.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	MemberService service;

	@GetMapping("/member/list")
	public void list(Model model, @RequestParam(value = "page", defaultValue = "1") int page) throws Exception {
		PageInfo<Member> pi = service.getPage(page);
		model.addAttribute("pi", pi);
	}

	@GetMapping("/member/create")
	public void getCreate(Member member, Model model) throws Exception {
		List<UserLevel> list = new ArrayList<>();

		for (UserLevel ul : UserLevel.values()) {
			list.add(ul);
		}
		model.addAttribute("ulList", list);
		model.addAttribute("userLevels", UserLevel.values());
	}

	@PostMapping("/member/create")
	public String postCreate(@Valid Member member, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "password2") String password2,
			@RequestParam("avata") MultipartFile file,
			BindingResult result) throws Exception {
		if (member.getPassword().equals(password2) == false) {
			log.info("유효성 검증 실패");
			return "redirect:list?page=" + page;
		}
		if (result.hasErrors()) {
			log.info("유효성 검증 실패");
			return "redirect:/list";
		}
		service.create(member, file);

		return "redirect:list?page=" + page;
	}

	@GetMapping("/member/view/{userId}")
	public String view(@PathVariable String userId, Model model) throws Exception {
		System.out.println("userID : " + userId);
		Member member = service.getMember(userId);
		model.addAttribute("member", member);

		return "admin/member/view";
	}

	@GetMapping("/member/id-check/{userId}")
	@ResponseBody
	public ResponseEntity<ResultMsg> checkId(@PathVariable String userId) throws Exception {
		if (service.getMember(userId) == null) {
			System.out.println("idcheck");
			return ResultMsg.response("ok", "사용가능한 ID 입니다.");
		} else {
			return ResultMsg.response("duplicate", "이미 사용중인 ID 입니다.");
		}
	}

	@GetMapping("member/edit/{userId}")
	public String getEdit(@PathVariable String userId, Model model) throws Exception {
		Member member = service.getMember(userId);
		model.addAttribute("member", member);
		model.addAttribute("userLevels", UserLevel.values());

		return "admin/member/edit";
	}

	@PostMapping("member/edit/{userId}")
	public String postEdit(@RequestParam(value = "page") int page, @Valid Member member, BindingResult result,
			@RequestParam("avata") MultipartFile file, Model model) throws Exception {
		model.addAttribute("userLevels", UserLevel.values());
		if (result.hasErrors()) {
			return "admin/member/edit";
		}
		if (service.updateByAdmin(member, file)) {
			return "redirect:/admin/member/view/" + member.getUserId() + "?page=" + page;
		} else {
			FieldError fieldError = new FieldError("member", "password", "비밀번호가 일치하지 않습니다");
			result.addError(fieldError);
			return "admin/member/edit";
		}
	}

	@DeleteMapping("member/delete/{userId}")
	@ResponseBody
	public ResponseEntity<ResultMsg> delete(@PathVariable String userId,
			@RequestParam(value = "password") String password) throws Exception {
		System.out.println("id = " + userId + ", pw = " + password);
		
		if (service.delete(userId, password)) {
			return ResultMsg.response("success", "삭제했습니다.");
		} else {
			return ResultMsg.response("fail", "비밀번호가 일치하지 않습니다.");
		}
	}
}
