package edu.autocar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.autocar.dao.AnswerDao;
import edu.autocar.domain.AnswerVO;

@Repository
public class AnswerServiceImpl implements AnswerService {
	@Autowired
	AnswerDao dao;
	
	@Override
	public List<AnswerVO> select(int rno) {
		return dao.select(rno);
	}

	@Override
	public int insert(AnswerVO answer) {
		return dao.insert(answer);
	}

	@Override
	public int delete(int ano) {
		System.out.println("서비스에서 " + ano + "글삭중");
		return dao.delete(ano);
	}
	
	@Override
	public int deleteByReply(int rno) {
		return dao.deleteByReply(rno);
	}

	@Override
	public int update(AnswerVO answer) {
		return dao.update(answer);
	}

	

}
