<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags/util" prefix="iot"%>

<script
	src="${contextPath}/resources/bower_components/axios/dist/axios.min.js">
</script>

<script>
$(function() {
	
});
</script>
<h2 class="my-5 text-primary">
	<i class="fas fa-users"></i> 회원 목록
</h2>

<div>
	<div class="float-left">
		
	</div>
	
	<div class="float-right">
		
	</div>
</div>

<div class="text-right">
	<a href="create?page=${pi.page}"><i class="fas fa-user-plus"></i>
		추가</a> (총 : ${pi.totalCount} 명)
</div>
<table class="table table-striped table-hover">
	<tr>
		<th>No</th>
		<th>사용자 ID</th>
		<th>이름</th>
		<th>email</th>
		<th>전화번호</th>
		<th>등록일</th>
		<th></th>
	</tr>
	<c:forEach var="member" items="${pi.list}" varStatus="status">
		<tr>
			<td>${pi.totalCount - ((pi.page-1)*2) - status.index}</td>
			<td><a href="view/${member.userId}?page=${pi.page}">
					${member.userId} <iot:newToday test="${member.regDate}" />
			</a></td>
			<td>${member.name}</td>
			<td>${member.email}</td>
			<td>${member.phone}</td>
			<td><fmt:formatDate value="${member.regDate}"
					pattern="yyyy-MM-dd" /></td>
			<td class="text-right">
				<a href="edit/${member.userId }?page=${pi.page}"
					class="btn btn-primary btn-sm mr-2" title=" 수정">
					<i class="fas fa-user-times"></i></a>
				<button class="btn btn-danger btn-sm delete" data-user-id="${member.userId }" title="삭제">
					<i class="fas fa-user-times"></i>
				</button>
			</td>
		</tr>
	</c:forEach>
</table>

<iot:pagination pageInfo="${pi }"></iot:pagination>
