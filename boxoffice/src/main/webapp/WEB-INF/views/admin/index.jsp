<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta charset="UTF-8">
<title>MOVIES TALK</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">

<!-- Page font -->
<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">

<!-- Script -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</head>
<style>
body {
	position: relative;
}
</style>
<body data-spy="scroll" data-target=".navbar" data-offset="10">
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
		<ul class="navbar-nav">		
			<li class="nav-item">
				<a class="nav-link" href="#home">HOME</a>
			</li>
			<li class="nav-item">				
				<a class="nav-link" href="#daily_section">금일 박스오피스</a>
			</li>
		</ul>
	</nav>
	<section id="home" class="row mt-5">
		<img src="https://cdn.pixabay.com/photo/2017/07/27/15/24/movie-2545676_960_720.jpg"  style="height:400px; width:100%; padding:0px;" class="col-3">
		<img src="https://cdn.pixabay.com/photo/2016/03/21/15/10/marilyn-monroe-1270659_960_720.png" style="height:400px; width:100%; padding:0px;" class="col-3">
		<img src="https://cdn.pixabay.com/photo/2014/11/04/00/53/john-wayne-516144_960_720.jpg" style="height:400px; width:100%; padding:0px;" class="col-3">
		<img src="https://cdn.pixabay.com/photo/2019/05/01/01/19/cat-4169876_960_720.jpg" style="height:400px; width:100%; padding:0px;" class="col-3">
	</section>
	<section id="daily_section" class="container-fluid mt-5">
		<div class="row">
			<div class="col-md-6 text-center">
				<h2 class="section-heading">
					금일 박스오피스
					<a class="btn btn-info mb-2" href="/boxoffice/admin/">갱신</a>
					<a class="btn btn-light mb-2" href="/boxoffice/admin/delete">리스트 초기화</a>
				</h2>
				<h5 class="section-subheading text-muted">
					<fmt:parseDate value="${yesterday}" var="yesterday" pattern="yyyyMMdd"/>
					<fmt:formatDate value="${yesterday}" var="yesterday" pattern="yyyy-MM-dd"/>
					${yesterday} 기준
				</h5>
				<table class="table mt-4 text-center">
					<thead class="thead-light">
						<tr>
							<th>순위</th>
							<th>제목</th>
							<th>관람객</th>
							<th>누적관람객</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="movie" items="${dailyList}" varStatus="status">
							<tr>
								<td>
									${movie.rank }
									<c:if test="${movie.rankInten > 0}">
										<span class="badge badge-danger">+${movie.rankInten}</span>									
									</c:if>
									<c:if test="${movie.rankInten < 0}">
										<span class="badge badge-secondary">${movie.rankInten}</span>										
									</c:if>
								</td>
								<td>
									${movie.movieNm }
									<c:if test="${movie.rankOldAndNew.equals('NEW')}">
										<span class="badge badge-warning">${movie.rankOldAndNew}</span>									
									</c:if>
								</td>
								<td>
									${movie.audiCnt }
								</td>
								<td>
									${movie.audiAcc }
								</td>
							</tr>						
						</c:forEach>		
					</tbody>
				</table>
			</div>
			<div class="col-md-6 text-center">
				<h2 class="section-heading">
					week's boxoffice
					<a class="btn btn-warning" href="#">갱신</a>
				</h2>
			</div>
		</div>
	</section>
</body>
</html>