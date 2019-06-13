package son.minho.service;

import java.util.List;

import son.minho.domain.Score;

public interface ScoreService {
	int countMovieScore(int movieId) throws Exception;
	
	int create(Score score) throws Exception;
	
	List<Score> getScoreList(int movieId) throws Exception;
	
	int delete(int scoreId) throws Exception;
}
