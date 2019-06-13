package son.minho.service;

import java.util.List;

import son.minho.domain.Movie;
import son.minho.domain.PageInfo;

public interface MovieService {
	int count() throws Exception;
	
	List<Movie> getMovieList(int start, int end) throws Exception;
	
	PageInfo<Movie> getPage(int page) throws Exception;
	
	int create(Movie movie) throws Exception;
	
	Movie getMovie(int id) throws Exception;
	
	int delete(int movieId) throws Exception;
	
	int update(Movie movie) throws Exception;
}
