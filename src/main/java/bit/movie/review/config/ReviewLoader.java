package bit.movie.review.config;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bit.movie.review.dao.DaumDAO;
import bit.movie.review.dao.NaverDAO;
import bit.movie.review.dao.ReviewDAO;
import bit.movie.review.dto.Daum;
import bit.movie.review.dto.Naver;
import bit.movie.review.dto.Review;

@Component
public class ReviewLoader {
	@Autowired
	SqlSession sqlSession;
	
//	@Scheduled(fixedDelay = 10000)
	public void naverReviewLoader() throws Exception {
		NaverDAO naverDAO = sqlSession.getMapper(NaverDAO.class);
		ReviewDAO reviewDAO = sqlSession.getMapper(ReviewDAO.class);
		
		List<Naver> naverList = naverDAO.selectAllNaver();
		
		//리뷰 접근
		Document docNaver = Jsoup.connect("https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=173576&type=after&onlyActualPointYn=N&order=newest").get();
		
		Elements docNaverHasNext = docNaver.select(".pg_next"); 
		int page = 0 ;
		for(int n = 0 ; n < naverList.size() ; n++) {
			while(true) {
				page++;
				
				Document docNaverPaging = Jsoup.connect("https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code="+naverList.get(n).getCode()+"&type=after&onlyActualPointYn=N&order=newest&page="+page).get();
				docNaverHasNext = docNaverPaging.select(".pg_next");
				Elements docNaverReviewList = docNaverPaging.select(".score_result li");
				
				for(int i = 0 ; i < docNaverReviewList.size() ; i++) {
					Elements docNaverReviewStarRating = docNaverReviewList.get(i).select(".star_score em");
					Elements docNaverReviewContents = docNaverReviewList.get(i).select(".score_reple p");
					Elements docNaverReviewWriter = docNaverReviewList.get(i).select(".score_reple dt span");
					Elements docNaverReviewCreated = docNaverReviewList.get(i).select(".score_reple dt em:nth-child(2)");
					Elements docNaverReviewID = docNaverReviewList.get(i).select(".score_reple dt a");
					
					String docId = docNaverReviewID.attr("onclick");
					int start = docId.indexOf("(")+1;
					int end = docId.indexOf(",");
					int id = Integer.parseInt(docId.substring(start,end));
					
					Review review = new Review();
					review.setId(id);
					review.setStarRating(Integer.parseInt(docNaverReviewStarRating.text()));
					review.setContents(docNaverReviewContents.text());
					review.setWriter(docNaverReviewWriter.text());
					review.setCreated(docNaverReviewCreated.text().replace(".", "-"));
					review.setCode(naverList.get(n).getCode());
					
					if(reviewDAO.selectOneReviewById(review).size() == 0 ) {
						reviewDAO.insertReview(review);
					}
				}
				
				if(page >=10) {
					break;
				}else if(docNaverHasNext.size() == 0) {
					break;
				}
			}
		}
	}
	
//	@Scheduled(fixedDelay = 60000)
	public void daumReviewLoader() throws Exception {
		System.out.println("다음 리뷰 접근 ");
		DaumDAO daumDAO = sqlSession.getMapper(DaumDAO.class);
		ReviewDAO reviewDAO = sqlSession.getMapper(ReviewDAO.class);
		List<Daum> daumList = daumDAO.selectAllDaum();
		for(int i = 1 ; i < daumList.size() ; i++) {
			//System.out.println(docDaum.select(".link_page:not(.link_prev):not(.link_next)").size())  이전,다음페이지 없애고
			int page = 1;
			while(true) {
				Document docDaum = Jsoup.connect("https://movie.daum.net/moviedb/grade?movieId="+daumList.get(i).getCode()+"&type=netizen&page="+page).get();
				Elements docReview = docDaum.select(".review_info");
				
				if(docReview.size() == 0) {
					break;
				}else if (page==10) {
					break;
				}
				Elements docWriter = docDaum.select(".link_profile");
				Elements docStarRating = docDaum.select(".emph_grade");
				Elements docContents = docDaum.select(".desc_review");
				Elements docCreated = docDaum.select(".info_append");
				Elements docId = docDaum.select(".review_info .link_delete");
				
				
				for(int j = 0 ; j < docReview.size() ; j++) {
					String id = docId.get(j).attr("onclick");
					int start = id.indexOf("(")+1;
					int end = id.indexOf(")");
					id = id.substring(start, end);
					
					Review review = new Review();
					
					review.setId(Integer.parseInt(id));
					review.setCode(daumList.get(i).getCode());
					review.setContents(docContents.get(j).text().trim());
					review.setCreated(docCreated.get(j).text().replace(".","-").replace(",",""));
					
					review.setStarRating(Integer.parseInt(docStarRating.get(j).text()));
					review.setWriter(docWriter.get(j).text());

					if(reviewDAO.selectOneReviewById(review).size() == 0) {
						reviewDAO.insertReview(review);
					}
				}
				
				page++;
			}
		}
	}
	
}
