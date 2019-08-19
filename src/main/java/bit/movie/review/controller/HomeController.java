package bit.movie.review.controller;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
  @Autowired
  SqlSession sqlSession;
  
  @RequestMapping(value="interceptorTest")
  public ModelAndView interceptorTest() throws Exception{
    ModelAndView mv = new ModelAndView("");
    return mv;
  }
  
  @RequestMapping(value="/")
  public ModelAndView GetMovie() throws Exception{

    
    
    ModelAndView mav = new ModelAndView();
    mav.setViewName("index");
    return mav;
  }
}
