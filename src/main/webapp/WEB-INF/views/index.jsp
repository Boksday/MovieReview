<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/index.css" />
</head>
<body>
	<h1>현재 상영영화</h1>

	<c:forEach items="${movieList}" var="movieList">
		<a href="/movieDetail?naverCode=${movieList.naverCode}">
			<div class="movieWrapper">
				<div class="posterWrapper">
					<img src="${movieList.posterImgSrc}" />
				</div>
				<div class="contentWrapper">
					<div class="ratingIcon">
						<img class="rating" src="/resources/images/${movieList.movieAge}" />
						<span class="title"> ${movieList.movieName} </span>
					</div>
				</div>
				<div class="contentWrapper">

					<span class="ticketingText">예매율</span> <span class="ticketing">
						<c:if test="${movieList.ticketing != null}">${movieList.ticketing}</c:if>
						<c:if test="${movieList.ticketing == null}">0.01미만</c:if>
					</span>% 
					<c:if test="${!(movieList.audience == '')}">
						<span class="audience">누적관객수 : <span class="audienceCount">
								${movieList.audience}</span></span>
					</c:if>
				</div>
				<div class="contentWrapper">
					<span class="starOff"><span class="star"
						style="width:${movieList.naverStarRating*10}%"></span></span>네이버 : <span
						class="starNum">${movieList.naverStarRating}</span>
				</div>
				<div class="contentWrapper">
					<c:if test="${not empty movieList.daumStarRating}">
					<span class="starOff"><span class="star"
						style="width:${movieList.daumStarRating*10}%"></span></span>다음 : <span
						class="starNum">${movieList.daumStarRating}</span>
					</c:if>
				</div>


			</div>
		</a>
		<hr>
	</c:forEach>

</body>
</html>