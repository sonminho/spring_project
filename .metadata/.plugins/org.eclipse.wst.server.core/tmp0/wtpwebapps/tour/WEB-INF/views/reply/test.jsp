<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags/util" prefix="iot"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.modal-reply {
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	padding: 10px;
	z-index: 1000;
}
.modal-answer {
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	padding: 10px;
	z-index: 1000;
}
</style>
<script>
	function getFormatDate(date){
		var year = date.getFullYear();                                 //yyyy
		var month = (1 + date.getMonth());                     //M
		month = month >= 10 ? month : '0' + month;     // month 두자리로 저장
		var day = date.getDate();                                        //d
		day = day >= 10 ? day : '0' + day;                            //day 두자리로 저장
		return  year + '/' + month + '/' + day;
	}

	function getAllList() {
		console.log("/tour/replies/${bno}/${pi.page}");
		$
				.getJSON(
						"/tour/replies/${bno}/${pi.page}",
						function(data) {
							var str = "";

							console.log(data.length);

							$(data.pi.list)
									.each(
											function() {
												str += "<tr>"
														+ "<td>"
														+ this.replyer
														+ "</td>"
														+ "<td>"
														+ this.replytext
														+ "</td>"
														+ "<td>"
														+ this.regDate
														+ "</td>"
														+ "<td data-rno="+this.rno+" data-replytext="+this.replytext+" data-replyer=" +this.replyer+">"
														+ "<button id='repBtn' class='btn btn-secondary'>댓글수정</button></td>"
														+ "</tr>";
												var replyer = this.replyer;
												$
														.each(
																this.list,
																function() {
																	var updatedate = (new Date(this.updateDate));
																	updatedate = getFormatDate(updatedate);									
																	str += "<tr class='table-light'><td>"
																			+ this.replyer
																			+ "</td>"
																			+ "<td><a href='#'>@"
																			+ replyer
																			+ "</a>&nbsp;"
																			+ this.replytext
																			+ "</td>"
																			+ "<td>"
																			+ updatedate
																			+ "</td>"
																			+ "<td data-replyer="+replyer+" data-ano="+this.ano+" data-rno="+this.rno+" data-replytext="+this.replytext+">"
																			+ "<button id='ansBtn' class='btn btn-dark'>답글수정</button></td>"
																			+ "</tr>";
																});
											});
							$("#replies").html(str);
						});
	}
</script>
<script>
	getAllList();
	$(function() {
		$("#replyAddBtn").on("click", function() {
			var replyer = $('#newReplyWriter').val();
			var replytext = $('#newReplyText').val();
			var bno = 22;

			$.ajax({
				type : 'post',
				url : '/tour/replies/',
				headers : {
					"content-type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : 'text',
				data : JSON.stringify({
					bno : bno,
					replyer : replyer,
					replytext : replytext
				}),
				success : function(result) {
					if (result == 'SUCCESS') {
						alert('댓글이 등록되었습니다.');
						$('#newReplyText').text("");
						getAllList();
					} else {
						alert('잘못된 경로입니다.');
					}
				}
			});
		});
		
		// 댓글
		$("#replies").on("click", "#repBtn", function() {
			$(".modal-answer").hide("slow");
			var reply = $(this).parent();
			var rno = reply.attr("data-rno");
			var replyer = reply.attr("data-replyer");
			var replytext = reply.attr("data-replytext");
			
			if(replyer == 'tyj') {
				return;
			} else {
				console.log('${pi}');
			}
			
			$("#reply-title").html(rno);
			$("#replyer").html(replyer);
			$("#replytext").val(replytext);
			$(".modal-reply").show("slow");
		});
		
		// 댓글 수정
		$('#replyModBtn').on('click', function() {
			var rno = $('#reply-title').html();
			var replytext = $('#replytext').val();
			console.log(rno + "답글을 " + replyer +"가 " + replytext + "로 수정합니다.");
			
			 if (confirm("정말 수정하시겠습니까??") == false)
					return;
			 
			$.ajax({
				type : 'put',
				url : '/tour/replies/' + rno,
				headers : {
					"content-type" : "application/json",
					"X-HTTP-Method-Override" : "PUT"
				},
				data : JSON.stringify({
					replytext : replytext
				}),
				success : function(result) {
					console.log("result : " + result);

					if (result == 'SUCCESS') {
						alert('수정되었습니다.');
						$(".modal-reply").hide("slow");
						getAllList();
					}
				}
			});
		});
		
		// 댓글 삭제
		$("#replyDelBtn").on("click", function() {
			var rno = $("#reply-title").html();
			var replytext = $("#replytext").val();
			
			 if (confirm("정말 삭제하시겠습니까?") == false)
				return;
			 
			$.ajax({
				type : 'delete',
				url : '/tour/replies/' + rno,
				headers : {
					"content-type" : "application/json",
					"X-HTTP-Method-Override" : "DELETE"
				},
				dataType : 'text',
				success : function(result) {
					console.log("result:" + result);
					if (result == "SUCCESS") {
						alert("삭제되었습니다");
						$(".modal-reply").hide("slow");
						$(location).attr('href', '/tour/replies/board/${bno}');
					}
				}
			});
		});
		
		// 답글 달기
		$('#replyAnsBtn').on('click', function() {
			var rno = $('#reply-title').html();
			var replytext = $('#replytext').val();
			var replyer = $('#replyer').html();
			
			console.log(replyer + ", " + rno + ", " + replytext);
			$.ajax({
				type : 'post',
				url : '/tour/replies/answer/' + rno,
				headers : {
					"content-type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : 'text',
				data : JSON.stringify({
					rno : rno,
					replyer : replyer,
					replytext : replytext
				}),
				success : function(result) {
					if (result == 'SUCCESS') {
						alert('답글이 등록되었습니다.');
						getAllList();
						$(".modal-reply").hide("slow");
					} else {
						alert('잘못된 경로입니다.');
					}
				}
			});
		});
		
		// 댓글 창 닫기
		$('.closeReplyBtn').on('click', function() {
			$(".modal-reply").hide("slow");
		});
		$('#replyCloswBtn').on('click', function() {
			$(".modal-reply").hide("slow");
		});
		
		// 답글
		$("#replies").on("click", "#ansBtn", function() {
			$(".modal-reply").hide("slow");
			var reply = $(this).parent();
			var ano = reply.attr("data-ano");
			var replyer = reply.attr("data-replyer");
			var replytext = reply.attr("data-replytext");
			
			$("#answer-title").html(ano);
			$("#answerer").html(replyer);
			$("#answertext").val(replytext);
			$(".modal-answer").show("slow");
		});
		
		// 답글 수정
		$('#answerModBtn').on('click', function() {
			var ano = $('#answer-title').html();
			var replyer = $("#answerer").html();
			var replytext = $('#answertext').val();
			
			console.log(ano + "답글을 " + replyer +"가 " + replytext + "로 수정합니다.");
			
			if (confirm("답글을 수정하시겠습니까??") == false)
					return;
			 
			$.ajax({
				type : 'put',
				url : '/tour/replies/answer/' + ano,
				headers : {
					"content-type" : "application/json",
					"X-HTTP-Method-Override" : "PUT"
				},
				data : JSON.stringify({
					ano : ano,
					replytext : replytext,
					replyer : replyer
				}),
				success : function(result) {
					console.log("result : " + result);

					if (result == 'SUCCESS') {
						alert('수정되었습니다.');
						$(".modal-answer").hide("slow");
						getAllList();
					}
				}
			});
		});
		
		// 답글 삭제
		$('#answerDelBtn').on('click', function() {
			var ano = $('#answer-title').html();
			var replyer = $("#answerer").html();
			var replytext = $('#answertext').val();
			
			console.log(ano + "답글을 " + replyer +"가 삭제합니다.");
			
			if (confirm("답글을 삭제하시겠습니까??") == false)
					return;
			 
			$.ajax({
				type : 'delete',
				url : '/tour/replies/answer/' + ano,
				headers : {
					"content-type" : "application/json",
					"X-HTTP-Method-Override" : "DELETE"
				},
				data : JSON.stringify({
					ano : ano,
					replytext : replytext,
					replyer : replyer
				}),
				success : function(result) {
					console.log("result : " + result);

					if (result == 'SUCCESS') {
						alert('삭제되었습니다.');
						$(".modal-answer").hide("slow");
						getAllList();
					}
				}
			});
		});
		// 답글 창 닫기
		$('.closeAnswerBtn').on('click', function() {
			$(".modal-answer").hide("slow");
		});		
		$('#answerCloswBtn').on('click', function() {
			$(".modal-answer").hide("slow");
		});
	});
</script>

<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">글 내용이 옵니다.</div>
		</div>


		<div class="jumbotron">
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th>작성자</th>
						<th>내용</th>
						<th>날짜</th>
						<th>기타</th>
					</tr>
				</thead>
				<tbody id="replies">

				</tbody>
			</table>

			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<input type='text' class="form-control" name='replyer'
						id='newReplyWriter' placeholder="작성자">
				</div>
				<input type="text" class="form-control" name="replytext"
					id="newReplyText" placeholder="댓글을 입력하세요.">
				<div class="input-group-append">
					<button id="replyAddBtn" class="btn btn-danger" type="button">등록</button>
					<button class="btn btn-secondary" type="button">취소</button>
				</div>
			</div>

			<div class="modal-reply" style='display: none'>
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title" id="reply-title"></h4>
						<button type="button" class="close closeReplyBtn" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<!-- <input type='text' id='replytext'> -->
						<div class="form-group">
							<label for="comment" id="replyer"></label>
							<textarea class="form-control" rows="5" id="replytext"></textarea>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" id="replyModBtn" class="btn btn-primary">수정</button>
						<button type="button" id="replyAnsBtn" class="btn btn-info">답글</button>
						<button type="button" id="replyDelBtn" class="btn btn-danger">삭제</button>						
						<button type="button" id="replyCloswBtn" class="btn btn-light closeBtn">닫기</button>
					</div>
				</div>
			</div>

			<div class="modal-answer" style='display: none'>
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title" id="answer-title"></h4>
						<button type="button" class="close closeAnswerBtn" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<!-- <input type='text' id='replytext'> -->
						<div class="form-group">
							<label for="comment" id="answerer"></label>
							<textarea class="form-control" rows="5" id="answertext"></textarea>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" id="answerModBtn" class="btn btn-primary">수정</button>
						<button type="button" id="answerDelBtn" class="btn btn-danger">삭제</button>						
						<button type="button" id="answerCloswBtn" class="btn btn-light closeBtn">닫기</button>
					</div>
				</div>
			</div>
			<iot:pagination pageInfo="${pi }"></iot:pagination>
		</div>
	</div>
</body>
</html>