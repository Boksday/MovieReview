package bit.movie.review.dao;

import java.util.List;

import bit.movie.review.dto.Movie;

public interface MovieDAO {
  public List<Movie> selectAllMovie();
  
  public Movie selectOneMovieById(int id);
  
  public Movie selectOneMovieByNaver(int codeNaver);
  
  public Movie selectOneMovieByDaum(int codeDaum);
  
  public void insertMovie(Movie movie);
  
  public void updateMovie(Movie movie);
  
  public void deleteMovie(Movie movie);
}