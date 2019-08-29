package bit.movie.review.dao;

import java.util.List;

import bit.movie.review.dto.Naver;

public interface NaverDAO {
	
	List<Naver> selectAllNaver();
	
	void insertNaver(Naver naver);
	
	void updateNaver(Naver naver);
}
