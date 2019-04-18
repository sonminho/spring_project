package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.autocar.domain.ReplyVO;

public interface ReplyDao extends CrudDao<ReplyVO, Integer>{
	public List<ReplyVO> list(Integer bno) throws Exception;
	
	public List<ReplyVO> getPage(@Param("start") int start, @Param("end") int end, @Param("bno") int bno) throws Exception;
	
	public int count(Integer bno);
	
}
