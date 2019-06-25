package multi.campus.dao;

import java.util.List;

import multi.campus.domain.DailyBoxOffice;

public interface DailyBoxOfficeDao extends CrudDao<DailyBoxOffice, Integer>{
	// 일간 박스오피스 리스트
	List<DailyBoxOffice> getAllDailyBoxOffice() throws Exception;
	
	// 주간 박스오피스 초기화
	int deleteAllBoxOffce() throws Exception;
}
