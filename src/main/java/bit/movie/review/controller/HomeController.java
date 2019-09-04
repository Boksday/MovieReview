package bit.movie.review.controller;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bit.movie.review.dao.MovieDAO;
import bit.movie.review.dao.ReviewDAO;
import bit.movie.review.dto.Movie;
import bit.movie.review.dto.Review;

@Controller
public class HomeController {
  @Autowired
  SqlSession sqlSession;
  
  @RequestMapping(value="/")
  public ModelAndView GetMovie() throws Exception{
    MovieDAO movieDAO = sqlSession.getMapper(MovieDAO.class);
    ModelAndView mav = new ModelAndView();
    mav.setViewName("index");
    mav.addObject("movieList",movieDAO.selectAllMovieJoinNaverDaum());
    return mav;
  }
  
  @RequestMapping(value="/movieDetail")
  public ModelAndView GetMovieDetail(int code) throws Exception{
	  MovieDAO movieDAO = sqlSession.getMapper(MovieDAO.class);
	  ReviewDAO reviewDAO = sqlSession.getMapper(ReviewDAO.class);
	  
	  Movie selectedMovie = movieDAO.selectOneMovieJoinNaverDaum(code);
	  
	  List<Review> reviewList = reviewDAO.selectOneMovieReview(selectedMovie);
	  ModelAndView mav = new ModelAndView();
	  mav.setViewName("movieDetail");
	  mav.addObject("movie", selectedMovie);
	  mav.addObject("reviewList",reviewList);
	  return mav;
  }
}
