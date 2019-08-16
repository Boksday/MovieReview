package com.movie.review.controller;


import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.movie.review.dto.Movie;

@Controller
public class TestController {
  
  @RequestMapping(value="interceptorTest")
  public ModelAndView interceptorTest() throws Exception{
    ModelAndView mv = new ModelAndView("");
    return mv;
  }
  
  @RequestMapping(value="/")
  public ModelAndView GetMovie() throws Exception{
    Document doc = Jsoup.connect("https://movie.naver.com/movie/running/current.nhn").get();
    Elements movieListDoc = doc.select(".lst_detail_t1 li");
    
    List<Movie> movieList = new ArrayList<Movie>();
    for(int i = 0 ; i < movieListDoc.size() ; i++) {
      
      Elements posterSrcDoc = movieListDoc.get(i).select(".thumb>a>img");
      Elements movieNameDoc = movieListDoc.get(i).select(".tit>a");
      Elements movieAgeDoc = movieListDoc.get(i).select(".tit>span");
      Elements starRatingDoc = movieListDoc.get(i).select(".star_t1>a>.num");
      Elements audienceDoc = movieListDoc.get(i).select(".num2>em");
      Elements ticketingDoc = movieListDoc.get(i).select(".info_exp .num");
      Movie movie = new Movie();
      movie.setMovieName(movieNameDoc.text());
      movie.setPosterImgSrc(posterSrcDoc.attr("src")); 
      
      if(movieAgeDoc.text().equals("전체 관람가")) {
        movie.setMovieAge("KMRB_A.png");
      }else if(movieAgeDoc.text().equals("12세 관람가")) {
        movie.setMovieAge("KMRB_B.png");
      }else if(movieAgeDoc.text().equals("15세 관람가")) {
        movie.setMovieAge("KMRB_C.png");
      }else if(movieAgeDoc.text().equals("청소년 관람불가")) {
        movie.setMovieAge("KMRB_D.png");
      }
      
      System.out.println(ticketingDoc);
      movie.setStarRating(Double.valueOf(starRatingDoc.text()));
      movie.setAudience(audienceDoc.text());
      System.out.println(movie.getMovieName() + " / '" + ticketingDoc.text() + "'");
      if(!ticketingDoc.text().trim().equals("")) {
        movie.setTicketing(ticketingDoc.text());
      }else {
        movie.setTicketing(null);
      }
      movieList.add(movie);
    }
    
    
    ModelAndView mav = new ModelAndView();
    mav.setViewName("index");
    mav.addObject("movieList",movieList);
    return mav;
  }
}
