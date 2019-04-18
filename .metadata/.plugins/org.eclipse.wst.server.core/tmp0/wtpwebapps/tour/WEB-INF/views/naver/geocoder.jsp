<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=rbeyz68rf5&submodules=geocoder">
	
</script>
<script>
	var mapOptions = {
		center : new naver.maps.LatLng(37.3595704, 127.105399),
		zoom : 10, // 1(최소)~14(최대), 기본 : 11
		scaleControl : false,
		logoControl : false,
		mapDataControl : false,
		zoomControl : true,
		zoomControlOptions : {
			style : naver.maps.ZoomControlStyle.SMALL
		}
	};
	var map = new naver.maps.Map('map', mapOptions);
	
	naver.maps.Service.geocode({
		address : '역삼'
	}, function(status, response) {
		console.log(status, response)
		if (status === naver.maps.Service.Status.ERROR) {
			return alert('Something wrong!');
		}
		var result = response.result, // 검색 결과의 컨테이너
		items = result.items; // 검색 결과의 배열
	});
</script>

</head>

<body>

</body>
</html>