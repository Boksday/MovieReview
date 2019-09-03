<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
</body>
</html>