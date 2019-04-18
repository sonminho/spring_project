package edu.autocar.service;

import java.util.List;

import edu.autocar.domain.PageInfo;
import edu.autocar.domain.ReplyVO;

public interface ReplyService {
	public List<ReplyVO> list(Integer bno) throws Exception;
	
	PageInfo<ReplyVO> getPage(Integer bno, Integer page) throws Exception;
	
	public int getCount(Integer bno) throws Exception;
	
	public void create(ReplyVO vo) throws Exception;
	
	public void update(ReplyVO vo) throws Exception;
	
	public void delete(Integer rno) throws Exception;
}
