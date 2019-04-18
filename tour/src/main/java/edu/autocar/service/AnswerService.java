package edu.autocar.service;

import java.util.List;

import edu.autocar.domain.AnswerVO;

public interface AnswerService {
	List<AnswerVO> select(int rno);

	int insert(AnswerVO answer);
	
	int update(AnswerVO answer);
	
	int delete(int ano);
	
	int deleteByReply(int rno);
}
