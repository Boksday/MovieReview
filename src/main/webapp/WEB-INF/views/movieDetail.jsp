<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${movie.movieName} 리뷰보기</title>
<link rel="stylesheet" href="resources/css/movieDetail.css" />
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
	<div class='movieWrapper'>
		<div class='movieHeadWrapper'>
			<img class='moviePoster' src="${movie.posterImgSrc}">
			</div>
			<div class='movieContentsWrapper'>
				<img class='movieAge' src="/resources/images/${movie.movieAge}" /> 
				<span class="movieName">${movie.movieName}</span>
				<div class='date'>${movie.date.replace('-','.')}개봉</div>
				<div class='audience'>누적관객 <span class="audienceNum">${movie.audience}</span></div>
				<div class='ticketing'>예매율 <span class="ticketingNum">${movie.ticketing}</span>%</div>
				<div class='naverStarRating'>네이버 평점 : ${movie.naverStarRating}</div>
				<div class='daumStarRating'>다음 평점 : ${movie.daumStarRating}</div>
			</div>
	</div>
	<hr>
	<div class="reviewTitle">
		리뷰
	</div>
		<c:choose>
			<c:when test="${not empty reviewList}"> 
			<div class="reviewsWrapper">
			
				<c:forEach var="review" items="${reviewList}">
					<div class="reviewWrapper">
						<div class="reviewHeadWrapper">
							<c:if test="${review.corp == '다음'}"><img class="corp"src="https://t1.daumcdn.net/cfile/tistory/226983445225989526"></c:if>
							<c:if test="${review.corp == '네이버'}"><img class="corp"src="https://t1.daumcdn.net/cfile/tistory/24703244522598961F"></c:if>
							<div class="writer">${ review.writer} 
							<span class="created">${review.created.substring(0,16)}</span>
		 
							</div>
						</div>
						<div class="reviewDetail">
							${review.contents}
						</div>
						<div class="starRatingWrapper">
								<span class="starOff"> 
									<span class="star" style="width:${review.starRating*10}%"></span>
								</span>
								<span class="starRatingText">${review.starRating} / 10</span>
						</div>
					</div>
				</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
			<div class="nonReview">등록된 리뷰가 없습니다. </div>
			</c:otherwise>
		</c:choose>
		<div class="buttonWrapper">
			<span class="pageNum"></span> / <span class="totalNum"></span>
		</div>
		
		<script>
			var review = $(".reviewWrapper");
			var totalpage = Math.ceil(review.length/5);
			var page = 1 ; 
			var pages = new Array();
			var reviewArray = new Array();
			var temp = 1;
			$(".reviewsWrapper").empty();
			
			if(totalpage > 0){
				$(".pageNum").append(page);
				$(".totalNum").append(totalpage);
				$(".buttonWrapper").append("<button id='next'>다음</button>");
				$("#next").click(nextButton);
			}
			
			
			for(var i = 1 ; i <= totalpage ; i++){
				for(var j = 1 ; j <= 5 ; j++){
					var reviewPage = {
							page : i,
							review : review.get(temp-1), 
					}
					reviewArray.push(reviewPage);
					temp++;
				}
				pages.push(page);
			}

			createdReview(page);
			
			function createdReview(pg){
				console.log(page + " /"); 
				for(var i = 0 ; i < reviewArray.length ; i++){
					if(reviewArray[i].page === pg){
						$(".reviewsWrapper").append(reviewArray[i].review); 
					}
				}
				if(page != 1 && $("#prev").val() == null ){
					$(".buttonWrapper").prepend("<button id='prev'>이전</button>");
					
					$("#prev").click(prevButton);
				}
				
				
			}

			 function prevButton(){
				$(".reviewsWrapper").empty(); 

				page--;
				$(".pageNum").text(page);
				createdReview(page);
				if(page == 1){
					$("#prev").remove();
				}else if($("#next").val() == null){
					$(".buttonWrapper").append("<button id='next'>다음</button>");
					$("#next").click(nextButton)
				}
			}
			
			function nextButton(){
				$(".reviewsWrapper").empty(); 
				
				page++;
				$(".pageNum").text(page);
				createdReview(page);
				if(page == totalpage){
					$("#next").remove();
				}else if($("#prev").val() == null){
					$(".buttonWrapper").prepend("<button id='prev'>다음</button>");
					$("#next").click(prevButton)
				}
			}

			</script>
</body>
</html>