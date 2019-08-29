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
import bit.movie.review.dto.Naver;

@Component
public class Scheduler {
	@Autowired
	private SqlSession sqlSession;

	public void movieScheduler() throws IOException {
		// 네이버 영화 Docs
		Document docNaver = Jsoup.connect("https://movie.naver.com/movie/running/current.nhn").get();
		Elements movieListDocNaver = docNaver.select(".lst_detail_t1 li");
		// 다음 영화 Docs
		Document docDaum = Jsoup.connect("https://movie.daum.net/premovie/released?opt=reserve&page=1").get();
		Elements movieListDocDaum = docDaum.select(".list_movie li");
		MovieDAO movieDAO = sqlSession.getMapper(MovieDAO.class);
		List<Movie> movieList = movieDAO.selectAllMovie();
		for (int i = 0; i < movieListDocNaver.size(); i++) {
			// 네이버 영화 불러오기
			Elements posterSrcDoc = movieListDocNaver.get(i).select(".thumb>a>img");
			Elements movieNameDoc = movieListDocNaver.get(i).select(".tit>a");
			Elements movieAgeDoc = movieListDocNaver.get(i).select(".tit>span");
			Elements starRatingDoc = movieListDocNaver.get(i).select(".star_t1>a>.num");
			Elements audienceDoc = movieListDocNaver.get(i).select(".num2>em");
			Elements ticketingDoc = movieListDocNaver.get(i).select(".info_exp .num");
			Elements codeDoc = movieListDocNaver.get(i).select(".tit>a");
			Elements dateDoc = movieListDocNaver.get(i).select("dl[class=lst_dsc] dl[class=info_txt1] dd");
			ArrayList<String> dateAr = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(dateDoc.text() + "", "|");
			while (st.hasMoreTokens()) {
				dateAr.add(st.nextToken());
			}
			String date = dateAr.get(2).split(" ")[1];
			date = date.replace(".", "-");
			Movie movie = new Movie();
			Naver naver = new Naver();

			movie.setDate(date);
			movie.setMovieName(movieNameDoc.text());
			movie.setPosterImgSrc(posterSrcDoc.attr("src"));
			naver.setCode(Integer.parseInt(codeDoc.attr("href").split("=")[1]));
			if (movieAgeDoc.text().equals("전체 관람가")) {
				movie.setMovieAge("KMRB_A.png");
			} else if (movieAgeDoc.text().equals("12세 관람가")) {
				movie.setMovieAge("KMRB_B.png");
			} else if (movieAgeDoc.text().equals("15세 관람가")) {
				movie.setMovieAge("KMRB_C.png");
			} else if (movieAgeDoc.text().equals("청소년 관람불가")) {
				movie.setMovieAge("KMRB_D.png");
			}
			// 끝-- 네이버영화 불러오기 --

			// 다음 영화

			// 중복확인
			boolean insertCheck = true;
			boolean updateCheck = false;

			for (Movie mov : movieList) {
				if (mov.getMovieName().equals(movie.getMovieName()) && mov.getDate().equals(movie.getDate())) {
					insertCheck = false;
					updateCheck = true;
				}
			}
			// 테이블에 저장
			if (insertCheck) {
				movieDAO.insertMovie(movie);
			}
			if (updateCheck) {
				movieDAO.updateMovie(movie);
			}
		}
		System.out.println("DD");
		for (int i = 1; i <= movieListDocDaum.size(); i++) {
			System.out.println(i);
			/*
			 * Elements movieNameDocDaum =
			 * movieListDocDaum.get(i).select(".wrap_movie .info_tit a");
			 * System.out.println("check1"); String[] movieHref =
			 * movieNameDocDaum.attr("href").split("="); System.out.println("check2");
			 * 
			 * Document docDaumMovie =
			 * Jsoup.connect("https://movie.daum.net/moviedb/main?movieId="+movieHref[
			 * movieHref.length-1]).get(); System.out.println("check3"); Elements
			 * movieDateDocDaum = docDaumMovie.getElementsByClass("list_movie");
			 * System.out.println("check4"); String daumDate =
			 * movieDateDocDaum.get(i).select(".txt_main:nth-child(odd)").text();
			 * System.out.println("check5"); daumDate =
			 * daumDate.trim().split(" ")[0].replace(".", "-");
			 * System.out.println("check6");
			 * 
			 * Movie movieDaum = new Movie(); System.out.println("check7");
			 * movieDaum.setCodeDaum(Integer.parseInt(movieHref[movieHref.length-1]));
			 * System.out.println("check8");
			 * movieDaum.setMovieName(movieNameDocDaum.text());
			 * System.out.println("check9"); movieDaum.setDate(daumDate);
			 * System.out.println("check10");
			 * 
			 * boolean updateCheck = true;
			 * 
			 * for(Movie movie : movieList) {
			 * if(movie.getMovieName().equals(movieDaum.getMovieName()) &&
			 * movie.getDate().equals(movieDaum.getDate())) {
			 * movie.setCodeDaum(movieDaum.getCodeDaum()); System.out.println("check"); } }
			 */
		}

	}
}
