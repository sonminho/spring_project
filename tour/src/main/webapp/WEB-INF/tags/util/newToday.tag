<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="test" required="true" type="java.util.Date"%>

<fmt:formatDate var="today" value="<%=new java.util.Date() %>" pattern="yyyy-MM-dd"/>
<fmt:formatDate var="testday" value="${test}" pattern="yyyy-MM-dd"/>
<c:if test="${today == testday }">
	<span class="badge badge-danger"><i class="fas fa-tag"></i>NEW</span>
</c:if>