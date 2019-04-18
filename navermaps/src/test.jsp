<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ClientId">
</script>
<script>
	$(function() {
		var mapOptions = {
				center: new naver.maps.LatLng(37.3595704,127.105399),
				zoom: 10 // 1(최소)~14(최대), 기본 : 11
		}
		
		var map = new naver.maps.Map('map', mapOptions);
	});
</script>
<body>
	<div id="map" style="width:100%; height:400px;"></div>
</body>
</html>