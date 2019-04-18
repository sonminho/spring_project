package edu.autocar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import edu.autocar.domain.AnswerVO;

public interface AnswerDao {
	@Select("select * from tbl_answer where rno=#{rno} order by regdate asc")
	List<AnswerVO> select(int rno);
	
	@Insert("insert into tbl_answer (ano, rno, replytext, replyer, regdate, updatedate) values (answer_seq.nextval, #{rno}, #{replytext}, #{replyer}, sysdate, sysdate)")
	int insert(AnswerVO answer);

	@Update("update tbl_answer set replytext=#{replytext}, updatedate=sysdate where ano=#{ano}")
	int update(AnswerVO answer);
	
	@Delete("delete from tbl_answer where ano=#{ano}")
	int delete(int ano);
	
	@Delete("delete from tbl_answer where rno=#{rno}")
	int deleteByReply(int rno);
}
