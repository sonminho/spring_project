<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage = "error.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
컨텍스트 패스 : <%= request.getContextPath() %> <br>
요청방식 : <%= request.getMethod() %> <br>
요청한 URL : <%= request.getRequestURL() %> <br>
요청한 URI : <%= request.getRequestURI() %> <br>
<% 
	String path = request.getContextPath();
	path += "/sekyung.jpg";
%>


<img src="<%=path%>">
<%@ include file="footer.jsp" %>
</body>
</html>