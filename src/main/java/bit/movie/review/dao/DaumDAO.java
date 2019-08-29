package bit.movie.review.dao;

import java.util.List;

import bit.movie.review.dto.Daum;

public interface DaumDAO {
	List<Daum> selectAllDaum();
	
	void insertDaum(Daum daum);
	
	void updateDaum(Daum daum);
}
