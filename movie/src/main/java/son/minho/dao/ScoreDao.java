package son.minho.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import son.minho.domain.Score;

public interface ScoreDao extends CrudDao<Score, Integer>{
	int countMovieScore(@Param("movieId") int movieId) throws Exception;
	
	List<Score> getScoreList(@Param("movieId") int movieId) throws Exception;
}
