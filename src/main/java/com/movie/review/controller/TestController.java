package com.movie.review.controller;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
  
  @RequestMapping(value="interceptorTest")
  public ModelAndView interceptorTest() throws Exception{
    ModelAndView mv = new ModelAndView("");
    return mv;
  }
  
  @RequestMapping(value="/")
  public ModelAndView GetMovie() throws Exception{
    Document doc = Jsoup.connect("https://movie.naver.com/movie/bi/mi/review.nhn?code=10822#").get();
    String posterImgSrc = doc.select(".poster>a>img").attr("src");
    
    ModelAndView mav = new ModelAndView();
    mav.setViewName("index");
    mav.addObject("src",posterImgSrc);
    return mav;
  }
}
