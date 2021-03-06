<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/index.css" />
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
	<center><h1>현재 상영영화</h1></center>
	<div class="searchMovie">
		<input type=text id="searchBox" placeholder="찾으시는 영화의 제목을 입력하세요.  ">
	</div>
	<div id="movieWrapper" >
	
	<c:forEach items="${movieList}" var="movieList">
		<c:choose>
			<c:when test="${not empty movieList.naverCode}">
				<c:set var="code" value="${movieList.naverCode}"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="code" value="${movieList.daumCode}"></c:set>
			</c:otherwise>
		</c:choose>
		<a href="/movieDetail?code=${code}" class="movie">
			<div>
				<div class="poster">
					<img id="poster" src="${movieList.posterImgSrc}"/>
				</div>
				<div class="contents">
					<div class="movieAge">
						<img id="movieAge" src="/resources/images/${movieList.movieAge}"/>
						<span class="movieName">
							${movieList.movieName}
						</span>
					</div>
					<div class="ticket">
						<span class="ticketingText">예매율</span> 
						<span class="ticketing">
							<c:if test="${not empty fn:trim(movieList.ticketing)}">${movieList.ticketing}%</c:if>
							<c:if test="${empty fn:trim(movieList.ticketing)}">0.01%미만</c:if>
						</span>
						 | 
						 <c:if test="${!(movieList.audience == '')}">
						<span class="audience">누적관객  <span class="audienceCount">
								${movieList.audience}</span></span>
					</c:if>
					</div>
					
				<c:choose>
					<c:when test="${not empty fn:trim(movieList.naverStarRating)}">
						<div class="contentWrapper">
							<span class="starOff"> <span class="star"
								style="width:${movieList.naverStarRating*10}%"></span></span>네이버 : <span
								class="starNum">${movieList.naverStarRating}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="noStarRating">
							등록된 네이버 평점이 없습니다.
						</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${not empty fn:trim(movieList.daumStarRating)}">
						<div class="contentWrapper">
							<span class="starOff"><span class="star"
								style="width:${movieList.daumStarRating*10}%"></span>
								</span>다음 : <span
								class="starNum">${movieList.daumStarRating}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="noStarRating">
							다음 평점이 없습니다.
						</div>
					</c:otherwise>
				
				</c:choose>
				</div>
			</div>
		  </a>
	</c:forEach>
	</div>
	<script type="text/javascript" src="resources/js/index.js" ></script>
</body>
</html>