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
<script>
	$(function() {
		$('.create-score-btn').on('click', function() {
			var score = $('#score-select').val();
			var movieId = $(this).siblings('span').text();
			var content = $('#content-text').val();
			console.log(score + " , " + movieId + "," + content);
			var url = '/movie/score/' + movieId;

			var data = new Object();
			data.content = content;
			data.score = score;
			data.movieId = movieId;
			
			$.ajax({
				url : url,
				type : "POST",
				data : JSON.stringify(data),
				dataType : 'text',
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				success : function(data) {
					console.log(JSON.parse(data));
					if (JSON.parse(data).result == "ok") {
						console.log('등록완료');
						location = "/movie/detail/" + movieId;
					} else {
						console.log('실패')
					}
				}
			});
		});
		
		$('.delete-score-btn').on('click', function() {
			var scoreId = $(this).prev('span').text();
			console.log(scoreId);
			
			if(confirm('삭제할까요?')) {
				$(this).closest('tr').remove();
				$.ajax({
					url: '/movie/score/delete/' + scoreId,
					type: "GET",
					success: function(data) {
						if(data.result == "ok") {
							alert("삭제완료하였습니다.");
						}
					}
				});
			}
		});
		
		$('.list-btn').on('click', function() {
			location = "/movie/";
		});
	});
</script>
<body>
	<div class="container">
		<div class="jumbotron mt-3">
			<h2>${movie.title}</h2>
			<p>${movie.audience}명 관람</p>
			<hr>
			<img src="${movie.posterUrl}">
			<hr>
			<h5>${movie.description}</h5>
			<div class="form-group mt-3 mb-3">
				<select id="score-select" class="form-control mt-3" name="score">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5" selected="selected">5</option>
				</select> <input id="content-text" class="form-control mt-3" type="text"
					name="content" placeholder="한줄평"> <span
					style="display: none">${movie.id}</span>
				<button class="form-control btn btn-warning create-score-btn mt-3">평점주기</button>
				<button class="form-control btn btn-white list-btn mt-3">목록으로</button>
			</div>
			
			<table class="table mt-5">
				<thead class="thead-dark">
					<tr class="row">
						<th class="col-2 text-center">점수</th>
						<th class="col-8 text-center">코멘트</th>
						<th class="col-2 text-center">삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="score" items="${scoreList}" varStatus="status">						
						<tr class="row">							
							<td class="col-2 text-center">${score.score}</td>
							<td class="col-8 text-center">${score.content}</td>
							<td class="col-2 text-center">
								<span style="display:none;">${score.id}</span>
								<button class="btn btn-danger delete-score-btn">삭제</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</div>
</body>
</html>