package multi.campus.dao;

import multi.campus.domain.MovieActor;
import multi.campus.domain.MovieGenre;
import multi.campus.domain.MovieInfo;

public interface MovieInfoDao extends CrudDao<MovieInfo, Integer> {
	int insertGenre(MovieGenre movieGenre) throws Exception;
	
	int insertActor(MovieActor movieActor) throws Exception;
}
