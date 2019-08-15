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
    Elements movieListDoc = doc.select(".lst_detail_t1");
    Elements posterSrcDoc = movieListDoc.select(".thumb>a>img");
    Elements movieNameDoc = movieListDoc.select(".tit>a");
    Elements movieAgeDoc = movieListDoc.select(".tit>span");
    Elements starRatingDoc = movieListDoc.select(".star_t1>a>.num");
    Elements audienceDoc = movieListDoc.select(".num2>em");
    Elements ticketingDoc = movieListDoc.select("li dl dd dl dd div .num");
    List<Movie> movieList = new ArrayList<Movie>();
    for(int i = 0 ; i < posterSrcDoc.size() ; i++) {
      Movie movie = new Movie();
      movie.setMovieName(movieNameDoc.get(i).text());
      movie.setPosterImgSrc(posterSrcDoc.get(i).attr("src"));
      
      if(movieAgeDoc.get(i).text().equals("전체 관람가")) {
        movie.setMovieAge("KMRB_A.png");
      }else if(movieAgeDoc.get(i).text().equals("12세 관람가")) {
        movie.setMovieAge("KMRB_B.png");
      }else if(movieAgeDoc.get(i).text().equals("15세 관람가")) {
        movie.setMovieAge("KMRB_C.png");
      }else if(movieAgeDoc.get(i).text().equals("청소년 관람불가")) {
        movie.setMovieAge("KMRB_D.png");
      }
      
      movie.setStarRating(Double.valueOf(starRatingDoc.get(i).text()));
      movie.setAudience(audienceDoc.get(i).text());
      movie.setTicketing(ticketingDoc.get(i).text());
      
      movieList.add(movie);
    }
    
    
    ModelAndView mav = new ModelAndView();
    mav.setViewName("index");
    mav.addObject("movieList",movieList);
    return mav;
  }
}
