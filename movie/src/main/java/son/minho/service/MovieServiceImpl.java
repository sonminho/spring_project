package son.minho.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import son.minho.dao.MovieDao;
import son.minho.domain.Movie;
import son.minho.domain.PageInfo;

@Repository
public class MovieServiceImpl implements MovieService{
	@Autowired
	MovieDao movieDao;
	
	static final int PER_PAGE_COUNT = 3;
	
	@Override
	public int count() throws Exception {
		return movieDao.count();
	}

	@Override
	public List<Movie> getMovieList(int start, int end) throws Exception {
		return movieDao.getPage(start, end);
	}

	@Override
	public int create(Movie movie) throws Exception {
		return movieDao.insert(movie);
	}

	@Override
	public int delete(int movieId) throws Exception {
		return movieDao.delete(movieId);
	}

	@Override
	public int update(Movie movie) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Movie getMovie(int id) throws Exception {
		return movieDao.getMovie(id);
	}

	@Override
	public PageInfo<Movie> getPage(int page) throws Exception {
		int start = (page-1) * PER_PAGE_COUNT;
		int end = start + PER_PAGE_COUNT;
		int totalCount = movieDao.count();
		
		List<Movie> list = movieDao.getPage(start+1, end);
		
		return new PageInfo<>(totalCount, (int)Math.ceil(totalCount / (double)PER_PAGE_COUNT), page, PER_PAGE_COUNT, list);
	}
	
}
