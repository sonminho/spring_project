package edu.autocar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.autocar.domain.AnswerVO;
import edu.autocar.domain.PageInfo;
import edu.autocar.domain.ReplyVO;
import edu.autocar.service.AnswerService;
import edu.autocar.service.ReplyService;

@Controller
@RequestMapping("/replies")
public class ReplyController {
	@Autowired
	ReplyService service;

	@Autowired
	AnswerService answerService;
	
	@RequestMapping(value = "/board/{bno}", method = RequestMethod.GET)
	public String ajaxTest(Model model, 
			@PathVariable Integer bno, 
			@RequestParam(value = "page", defaultValue = "1") int page) throws Exception {
		PageInfo<ReplyVO> pi = service.getPage(bno, page);
		model.addAttribute("pi", pi);
		model.addAttribute("bno", bno);
		
		return "reply/test";
	}
	
	@ResponseBody
	@RequestMapping(value="/", method = {RequestMethod.POST})
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		System.out.println("댓글 추가 요청\n" + vo);
		try {
			service.create(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@GetMapping("/all/{bno}")
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno) {
		ResponseEntity<List<ReplyVO>> entity = null;
		System.out.println(bno +"글 리스트 출력 요청");
		try {
			entity = new ResponseEntity<List<ReplyVO>>(service.list(bno), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno,
			@RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		
		try {
			vo.setRno(rno);
			service.update(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@Transactional
	@RequestMapping(value="/{rno}", method = {RequestMethod.DELETE})
	public ResponseEntity<String> remove(@PathVariable("rno") Integer rno) {
		ResponseEntity<String> entity = null;
		System.out.println(rno + "댓글 삭제요청");
		Map<String, Object> map = new HashMap<>();
		
		try {
			answerService.deleteByReply(rno);
			service.delete(rno);
			map.put("res", "SUCCESS");
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			map.put("res", "FAIL");
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@Transactional
	@RequestMapping(value="/{bno}/{page}", method = {RequestMethod.GET})
	public ResponseEntity<Map<String, Object>> getPage(
			@PathVariable("bno")Integer bno,
			@PathVariable("page")Integer page,
			Model model) {
		ResponseEntity<Map<String, Object>> entity = null;
		System.out.println("----------------------------------------------");
		try {
			PageInfo<ReplyVO> pi = service.getPage(bno, page);
			
			for(ReplyVO rVo : pi.getList()) {
				rVo.setList(answerService.select(rVo.getRno()));
				System.out.print(rVo);
			}
			model.addAttribute("pi", pi);
			Map<String, Object> map = new HashMap<>();
			map.put("pi", pi);
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
 	
	@ResponseBody
	@RequestMapping(value="/answer/{rno}", method = {RequestMethod.POST})
	public ResponseEntity<String> addAnswer(@PathVariable("rno") Integer rno,
			@RequestBody ReplyVO replyVO) {
		ResponseEntity<String> entity = null;
		System.out.println(rno + "글에 답글 추가요청" + replyVO);
		Map<String, Object> map = new HashMap<>();
		
		AnswerVO answerVO = new AnswerVO();
		answerVO.setRno(rno);
		answerVO.setReplyer(replyVO.getReplyer());
		answerVO.setReplytext(replyVO.getReplytext());
		
		try {
			answerService.insert(answerVO);
			map.put("res", "SUCCESS");
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			map.put("res", "FAIL");
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="/answer/{ano}", method = {RequestMethod.PUT})
	public ResponseEntity<String> modAnswer(@PathVariable("ano") Integer ano,
			@RequestBody ReplyVO replyVO) {
		ResponseEntity<String> entity = null;
		
		Map<String, Object> map = new HashMap<>();
		
		AnswerVO answerVO = new AnswerVO();
		answerVO.setAno(ano);
		answerVO.setReplyer(replyVO.getReplyer());
		answerVO.setReplytext(replyVO.getReplytext());
		System.out.println(ano + "글에 답글 데이터 수정요청" + answerVO);
		
		try {
			map.put("res", "SUCCESS");
			answerService.update(answerVO);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			map.put("res", "FAIL");
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="/answer/{ano}", method = {RequestMethod.DELETE})
	public ResponseEntity<String> delAnswer(@PathVariable("ano") Integer ano,
			@RequestBody ReplyVO replyVO) {
		ResponseEntity<String> entity = null;
	
		Map<String, Object> map = new HashMap<>();
		System.out.println(ano + "글에 답글 데이터 삭제요청");
		
		try {
			map.put("res", "SUCCESS");
			answerService.delete(ano);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			map.put("res", "FAIL");
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
