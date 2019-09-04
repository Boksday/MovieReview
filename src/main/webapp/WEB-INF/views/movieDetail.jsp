<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${movie.movieName}리뷰보기</title>
<link rel="stylesheet" href="resources/css/movieDetail.css" />
</head>
<body>
	<div class='movieWrapper'>
		<div class='movieHeadWrapper'>
			<img class='moviePoster' src="${movie.posterImgSrc}">
			</div>
			<div class='movieContentsWrapper'>
				<img class='movieAge' src="/resources/images/${movie.movieAge}" /> 
				<span class="movieName">${movie.movieName}</span>
				<div>${movie.date.replace('-','.')}개봉</div>
				<div>누적관객 ${movie.audience}</div>
				<div>예매율 ${movie.ticketing}%</div>
				<div>네이버 평점 : ${movie.naverStarRating}</div>
				<div>다음 평점 : ${movie.daumStarRating}</div>
			</div>
	</div>
	<hr>
		리뷰
		<c:choose>
			<c:when test="${not empty reviewList}"> 
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
			</c:when>
			<c:otherwise>
			<div class="nonReview">등록된 리뷰가 없습니다. </div>
			</c:otherwise>
		</c:choose>

</body>
</html>