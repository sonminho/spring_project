package edu.autocar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.autocar.dao.BoardDao;
import edu.autocar.domain.Board;
import edu.autocar.domain.PageInfo;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class BoardServiceImpl implements BoardService {
	final static private int PER_PAGE_COUNT = 10;
	
	@Autowired
	BoardDao dao;

	@Override
	public PageInfo<Board> getPage(int page) throws Exception {
		int start = (page - 1) * PER_PAGE_COUNT;
		int end = start + PER_PAGE_COUNT;
		int totalCount = dao.count();
		List<Board> list = dao.getPage(start+1, end);
		return new PageInfo<>(totalCount, (int) Math.ceil(totalCount / (double) PER_PAGE_COUNT), page, PER_PAGE_COUNT,
				list);
	}

	@Override
	@Transactional
	public Board getBoard(int boardId) throws Exception {
		dao.increaseReadCnt(boardId);
		return dao.findById(boardId);
	}

	@Transactional
	@Override
	public void create(Board board) throws Exception {
		dao.insert(board);
	}

	@Transactional
	@Override
	public boolean update(Board board) throws Exception {
		return dao.update(board) == 1;
	}

	@Transactional
	@Override
	public boolean delete(int boardId, String password) throws Exception {
		return dao.delete(boardId, password) == 1;
	}
}
