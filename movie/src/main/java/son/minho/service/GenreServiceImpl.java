package son.minho.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import son.minho.dao.GenreDao;
import son.minho.domain.Genre;

@Repository
public class GenreServiceImpl implements GenreService {
	@Autowired
	GenreDao genreDao;
	
	@Override
	public int count() throws Exception {		
		return genreDao.count();
	}

	@Override
	public List<Genre> getGenre(int start, int end) throws Exception {
		return genreDao.getPage(start, end);
	}

}
