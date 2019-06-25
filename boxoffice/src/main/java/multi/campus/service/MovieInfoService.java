package multi.campus.service;

import java.util.List;

import org.json.simple.JSONArray;

import multi.campus.domain.DailyBoxOffice;
import multi.campus.domain.MovieActor;
import multi.campus.domain.MovieGenre;
import multi.campus.domain.MovieInfo;

public interface MovieInfoService {
	// kobis 에서 박스오피스 10위까지 영화정보 받아오기
	JSONArray getKobisDailyMovieList(String targetDt) throws Exception;
	
	// DB에서 일간 박스오피스 리스트 가져오기
	List<DailyBoxOffice> getAllDailyBoxOffice() throws Exception;
	
	// 영화 정보 입력
	MovieInfo getMovieInfo(int movieCd) throws Exception;
	
	// 일간 박스오피스 정보 레코드 추가
	int createDailyBoxOffice(DailyBoxOffice dailyBoxOffice) throws Exception;
	
	// 영화 정보 레코드 추가
	int createMovieInfo(MovieInfo movieInfo) throws Exception;
	
	// 영화 장르 레코드 추가
	int createMovieGenre(MovieGenre movieGenre) throws Exception;
	
	// 영화 배우 레코드 추가
	int createMovieActor(MovieActor movieActor) throws Exception;
	
	// 일간 박스오피스 정보 삭제
	int deleteAllBoxOffce() throws Exception;
}
