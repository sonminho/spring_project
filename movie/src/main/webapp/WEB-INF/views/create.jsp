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
<body>
	<div class="container">
		<div class="jumbotron mt-3">
			<h2>영화 등록</h2><hr>
			<form:form modelAttribute="movie" class="form" action="/movie/create" method="POST">
				<div class="form-group">
					<label for="title">영화 제목</label> 
					<input type="text" class="form-control" id="title" name="title" >
				</div>
				<div class="form-group">
					<label for="audience">관객수</label>
					<input type="number" class="form-control" id="audience" name="audience">
				</div>
				<div class="form-group">
					<label for="poster_url">이미지URL</label>
					<input type="text" class="form-control" id="posterUrl" name="posterUrl">
				</div>
				<div class="form-group">
					<label for="description">설명</label>
					<textarea class="form-control" rows="5" id="description" name="description"></textarea>
				</div>
				<div class="form-group">
					<label for="genre">장르</label>
					<select class="form-control" id="genre" name="genreId">
						<c:forEach var="genre" items="${genreList}">
							<option value="${genre.id}">${genre.id}.${genre.name}</option>							
						</c:forEach>
					</select>
				</div>
				<button type="submit" class="btn btn-primary">등록</button>
				<button type="reset" class="btn btn-warning">재작성</button>
			</form:form>
		</div>
	</div>
</body>
</html>
