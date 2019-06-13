package son.minho.dao;

import org.apache.ibatis.annotations.Param;

import son.minho.domain.Movie;

public interface MovieDao extends CrudDao<Movie, Integer>{
	Movie getMovie(@Param("id") int id) throws Exception;
}
