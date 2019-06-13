<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta charset="UTF-8">
<title>test</title>
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
a {
	color: black;
}
</style>
<script>
	$(function() {
		$('.detail-btn').on('click', function() {
			var movie_id = $(this).prev('span').text();
			console.log(movie_id);
			location = "/movie/detail/" + movie_id;
		});
		$('.delete-btn').on(
				'click',
				function() {
					var movie_id = $(this).parent('.card-body')
							.children('span').text();
					console.log(movie_id);
					console.log()
					if (confirm('삭제하시겠습니까?')) {
						var card = $(this).closest('.col-sm-4');
						var url = "/movie/delete/" + movie_id;

						$.ajax({
							url : url,
							success : function(data) {
								if (data.result == "ok") {
									card.remove();
									console.log('삭제 완료');
								}
							},
							error : function() {

							}
						});
					} else {
						console.log('취소');
					}
				});
	});
</script>
<body>
	<div class="container">
		<div class="d-flex justify-content-end">
			<div class="p-2 bg-white">
				<a href="/movie/create">영화등록</a>
			</div>
		</div>
		<div class="jumbotron">
			<div class="row">
				<c:forEach var="movie" items="${movieList}" varStatus="status">
					<div class="col-sm-4">
						<div class="card mt-3">
							<img class="card-img-top" src="${movie.posterUrl}">
							<div class="card-body text-center">
								<h4 class="card-title">${movie.title}</h4>
								<p class="card-text">${movie.description}</p>
								<span style="display: none">${movie.id}</span>
								<button class="btn btn-warning detail-btn">상세보기</button>
								<button class="btn btn-danger delete-btn">삭제하기</button>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<ul class="pagination pagination-md justify-content-center mt-4">
			<c:forEach var="idx" begin="1" end="${pi.totalPage}">
				<c:choose>
					<c:when test="${pi.page == idx}">
						<li class="page-item active"><a class="page-link"
							style="background-color: #495057; border-color: #f8f9fa;"
							href="?page=${idx }">${idx }</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link"
							href="?page=${idx }">${idx }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
</body>
</html>
