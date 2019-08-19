package bit.movie.review.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bit.movie.review.dao.MovieDAO;
import bit.movie.review.dto.Movie;

@Component
public class Scheduler {
  @Autowired
  private SqlSession sqlSession;
  
  @Scheduled(fixedDelay=5000)
  public void TestScheduler() throws IOException {
    Document doc = Jsoup.connect("https://movie.naver.com/movie/running/current.nhn").get();
    Elements movieListDoc = doc.select(".lst_detail_t1 li");
    MovieDAO movieDAO = sqlSession.getMapper(MovieDAO.class);
    for(int i = 0 ; i < movieListDoc.size() ; i++) {
      
      Elements posterSrcDoc = movieListDoc.get(i).select(".thumb>a>img");
      Elements movieNameDoc = movieListDoc.get(i).select(".tit>a");
      Elements movieAgeDoc = movieListDoc.get(i).select(".tit>span");
      Elements starRatingDoc = movieListDoc.get(i).select(".star_t1>a>.num");
      Elements audienceDoc = movieListDoc.get(i).select(".num2>em");
      Elements ticketingDoc = movieListDoc.get(i).select(".info_exp .num");
      Elements codeDoc = movieListDoc.get(i).select(".tit>a");
      Elements dateDoc = movieListDoc.get(i).select("dl[class=lst_dsc] dl[class=info_txt1] dd");
      ArrayList<String> dateAr = new ArrayList<String>();
      StringTokenizer st = new StringTokenizer(dateDoc.text()+"", "|");
      while(st.hasMoreTokens()) {
        dateAr.add(st.nextToken());
      }
      String date = dateAr.get(2).split(" ")[1];
      date = date.replace(".", "-");
      
      
      Movie movie = new Movie();
      movie.setDate(java.sql.Date.valueOf(date));
      movie.setMovieName(movieNameDoc.text());
      movie.setPosterImgSrc(posterSrcDoc.attr("src")); 
      movie.setCodeNaver(Integer.parseInt(codeDoc.attr("href").split("=")[1]));
      
      if(movieAgeDoc.text().equals("전체 관람가")) {
        movie.setMovieAge("KMRB_A.png");
      }else if(movieAgeDoc.text().equals("12세 관람가")) {
        movie.setMovieAge("KMRB_B.png");
      }else if(movieAgeDoc.text().equals("15세 관람가")) {
        movie.setMovieAge("KMRB_C.png");
      }else if(movieAgeDoc.text().equals("청소년 관람불가")) {
        movie.setMovieAge("KMRB_D.png");
      }
      
      movie.setStarRating(Double.valueOf(starRatingDoc.text()));
      movie.setAudience(audienceDoc.text());
      if(!ticketingDoc.text().trim().equals("")) {
        movie.setTicketing(ticketingDoc.text());
      }else {
        movie.setTicketing(null);
      }
      List<Movie> movieList = movieDAO.selectAllMovie();
      boolean check = true;
      for(Movie mov : movieList) {
        //System.out.println(mov.getDate() + " / / " + movie.getDate());
        if(mov.getMovieName().equals(movie.getMovieName()) && mov.getDate().getTime() == movie.getDate().getTime()) {
            check = false;
          System.out.println(mov.getDate().toLocalDate().compareTo(movie.getDate().toLocalDate()));
        }
      }
      if(check) {
        movieDAO.insertMovie(movie);
      }
      
    }
  }
}
