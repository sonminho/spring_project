<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="pageInfo" required="true" type="edu.autocar.domain.PageInfo" %>

<ul class="pagination pagination-md justify-content-center mt-4">
	<c:forEach var="idx" begin="1" end="${pi.totalPage }">
		<c:choose>
			<c:when test="${pi.page == idx}">
				<li class="page-item active"><a class="page-link"
					href="?page=${idx }">${idx }</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${idx }">${idx }</a>
				</li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</ul>