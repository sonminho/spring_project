package edu.autocar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.autocar.dao.ReplyDao;
import edu.autocar.domain.Board;
import edu.autocar.domain.PageInfo;
import edu.autocar.domain.ReplyVO;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ReplyServiceImpl implements ReplyService {
	final static private int PER_PAGE_COUNT = 10;
	
	@Autowired
	ReplyDao dao;
	
	@Override
	public List<ReplyVO> list(Integer bno) throws Exception {
		return dao.list(bno);
	}

	@Override
	public void create(ReplyVO vo) throws Exception {
		dao.insert(vo);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		dao.update(vo);
	}

	@Override
	public void delete(Integer rno) throws Exception {
		dao.delete(rno);
	}

	@Override
	public int getCount(Integer bno) throws Exception {
		return dao.count(bno);
	}

	@Override
	public PageInfo<ReplyVO> getPage(Integer bno, Integer page) throws Exception {
		int start = (page - 1) * PER_PAGE_COUNT;
		int end = start + PER_PAGE_COUNT;
		int totalCount = dao.count(bno);
		List<ReplyVO> list = dao.getPage(start+1, end, bno);
		
		return new PageInfo<>(totalCount, (int) Math.ceil(totalCount / (double) PER_PAGE_COUNT), page, PER_PAGE_COUNT,
				list);
	}
	
}
