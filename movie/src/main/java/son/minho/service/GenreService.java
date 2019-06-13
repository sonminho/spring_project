package son.minho.service;

import java.util.List;

import son.minho.domain.Genre;

public interface GenreService {
	int count() throws Exception;
	
	List<Genre> getGenre(int start, int end) throws Exception;
}
