package bit.movie.review.config;

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

import bit.movie.review.dao.DaumDAO;
import bit.movie.review.dao.MovieDAO;
import bit.movie.review.dao.NaverDAO;
import bit.movie.review.dto.Daum;
import bit.movie.review.dto.Movie;
import bit.movie.review.dto.Naver;

@Component
public class MovieLoader {
	@Autowired
	private SqlSession sqlSession;

	@Scheduled(fixedDelay = 10000)
	public void naverMovieLoader() throws Exception {
		List<Movie> naverMovieList = naverMovie();
	}

	@Scheduled(fixedDelay = 10000)
	public void daumMovieLoader() throws Exception {
		daumMovie();
	}

	// 네이버 영화 불러오기
	public List<Movie> naverMovie() throws Exception {
		System.out.println("네이버");
		MovieDAO movieDAO = sqlSession.getMapper(MovieDAO.class);
		NaverDAO naverDAO = sqlSession.getMapper(NaverDAO.class);

		// 사이트 접근
		Document docNaver = Jsoup.connect("https://movie.naver.com/movie/running/current.nhn").get();
		Elements movieListDocNaver = docNaver.select(".lst_detail_t1 li");

		for (int i = 0; i < movieListDocNaver.size(); i++) {
			// 필요한 부분 긁어오기
			Elements posterSrcDoc = movieListDocNaver.get(i).select(".thumb>a>img");
			Elements movieNameDoc = movieListDocNaver.get(i).select(".tit>a");
			Elements movieAgeDoc = movieListDocNaver.get(i).select(".tit>span");
			Elements starRatingDoc = movieListDocNaver.get(i).select(".star_t1>a>.num");
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
			// 각 영화 상세페이지로접근 (누적관객수 때문)
			Document movieDetailDoc = Jsoup.connect("https://movie.naver.com/movie/bi/mi/basic.nhn?code="
					+ Integer.parseInt(codeDoc.attr("href").split("=")[1])).get();
			Elements movieDetail = movieDetailDoc.select(".step9_cont .count");

			String audience = "";
			if (!movieDetail.text().trim().equals("")) {
				audience = movieDetail.text().trim().split("\\(")[0];
			}
			// 영화 객체에 담는 작업
			Movie movie = new Movie();
			movie.setDate(date);
			movie.setMovieName(movieNameDoc.text());
			movie.setPosterImgSrc(posterSrcDoc.attr("src"));
			movie.setAudience(audience);

			if (movieAgeDoc.text().equals("전체 관람가")) {
				movie.setMovieAge("KMRB_A.png");
			} else if (movieAgeDoc.text().equals("12세 관람가")) {
				movie.setMovieAge("KMRB_B.png");
			} else if (movieAgeDoc.text().equals("15세 관람가")) {
				movie.setMovieAge("KMRB_C.png");
			} else if (movieAgeDoc.text().equals("청소년 관람불가")) {
				movie.setMovieAge("KMRB_D.png");
			}
			if (!ticketingDoc.text().trim().equals("")) {
				movie.setTicketing(ticketingDoc.text());
			} else {
				movie.setTicketing(null);
			}
			boolean insertCheck = true;
			boolean updateCheck = false;

			List<Movie> movieList = movieDAO.selectAllMovie();
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
			List<Naver> naverList = naverDAO.selectAllNaver();
			Naver naver = new Naver();

			naver.setCode(Integer.parseInt(codeDoc.attr("href").split("=")[1]));
			naver.setDate(date);
			naver.setMovieName(movieNameDoc.text());
			naver.setStarRating(Double.valueOf(starRatingDoc.text()));

			insertCheck = true;
			updateCheck = false;

			for (Naver nav : naverList) {
				if (naver.getMovieName().equals(nav.getMovieName()) && naver.getDate().equals(nav.getDate())) {
					insertCheck = false;
					updateCheck = true;
				}
			}
			if (insertCheck) {
				naverDAO.insertNaver(naver);
			} else if (updateCheck) {
				naverDAO.updateNaver(naver);
			}
		}
		return movieDAO.selectAllMovie();
	}

	// 다음영화 로드
	public List<Daum> daumMovie() throws Exception {
		System.out.println("다음");
		MovieDAO movieDAO = sqlSession.getMapper(MovieDAO.class);
		DaumDAO daumDAO = sqlSession.getMapper(DaumDAO.class);
		Document docDaumPage = Jsoup.connect("https://movie.daum.net/premovie/released?opt=reserve&page=1").get();
		Elements movieListDocDaumPage = docDaumPage.select(".num_page");
		for (int i = 1; i <= movieListDocDaumPage.size(); i++) {
			Document docDaum = Jsoup.connect("https://movie.daum.net/premovie/released?opt=reserve&page=" + i).get();
			Elements movieListDocDaum = docDaum.select(".list_movie li");
			for (int j = 0; j < movieListDocDaum.size(); j++) {
				Elements movieNameDocDaum = movieListDocDaum.get(j).select(".wrap_movie .info_tit a");
				Elements ticketingDocDaum = movieListDocDaum.get(j).select(".info_state");
				
				String[] movieHref = movieNameDocDaum.attr("href").split("=");
				
				Document docDaumMovie = Jsoup
						.connect("https://movie.daum.net/moviedb/main?movieId=" + movieHref[movieHref.length - 1])
						.get();
				Elements movieDateDocDaum = docDaumMovie.select(".list_movie");
				Elements movieAge  = docDaumMovie.select(".list_movie dd:nth-child(6)");
				String daumDate = movieDateDocDaum.select(".txt_main:nth-child(odd)").text();
				daumDate = daumDate.trim().split(" ")[0].replace(".", "-");

				Daum daum = new Daum();
				daum.setMovieName(movieNameDocDaum.text());
				daum.setDate(daumDate);
				daum.setCode(Integer.parseInt(movieHref[movieHref.length - 1]));
				daum.setStarRating(Double.parseDouble(docDaumMovie.select(".emph_grade").text().split(" ")[0]));

				boolean insertCheck = true;
				boolean updateCheck = false;
				List<Daum> daumList = daumDAO.selectAllDaum();

				for (Daum da : daumList) {
					if (da.getMovieName().equals(daum.getMovieName()) && da.getDate().equals(daum.getDate())) {
						insertCheck = false;
						updateCheck = true;
					}
				}

				if (insertCheck) {
					daumDAO.insertDaum(daum);
				} else if (updateCheck) {
					daumDAO.updateDaum(daum);
				}
				String ticketing = "";
				if(ticketingDocDaum.text().trim().contains("・")) {
					ticketing = ticketingDocDaum.text().split("・")[1].split(" ")[2].replace("%","").trim();
				}

				Elements posterDoc = docDaumMovie.select(".thumb_summary .img_summary");
				String posterSrc = posterDoc.attr("src").substring(2, posterDoc.attr("src").length());
				Movie movie = new Movie();
				movie.setMovieName(movieNameDocDaum.text());
				movie.setDate(daumDate);
				movie.setTicketing(ticketing);
				movie.setPosterImgSrc(posterSrc);
			
				if(movieAge.text().contains("관람")) {
					movie.setMovieAge(movieAge.text().split(",")[1].trim());
				}else {
					Elements movieAgeException = docDaumMovie.select(".list_movie dd:nth-child(7)");
					movie.setMovieAge(movieAgeException.text().split(",")[1].trim());
				}
				System.out.println(movie.toString());
				
				insertCheck = true;
				updateCheck = false;
				List<Movie> movieList = movieDAO.selectAllMovie();
				
				for(Movie mov : movieList) {
					if(mov.getMovieName().equals(movie.getMovieName()) && mov.getDate().equals(movie.getDate())) {
						insertCheck = false;
						updateCheck = true;
					}
				}
				if(insertCheck) {
					movieDAO.insertMovie(movie);
				}else if(updateCheck) {
					movieDAO.updateMovieWithoutAudience(movie);
				}
				
			}
		}
		return daumDAO.selectAllDaum();
	}
}
