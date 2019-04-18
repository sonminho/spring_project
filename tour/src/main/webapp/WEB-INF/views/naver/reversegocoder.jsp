<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript"
		src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=rbeyz68rf5&submodules=geocoder">		
	</script>
	<script>
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(onSuccessGeolocation, // 위치 정보 얻기 성공 시 핸들러 
			onErrorGeolocation); // 위치 정보 얻기 실패 시 핸들러 
		}

		naver.maps.Service.reverseGeocode({
			location : new naver.maps.LatLng(37.3595316, 127.1052133),
		}, function(status, response) {
			if (status !== naver.maps.Service.Status.OK) {
				return alert('Something wrong!');
			}
			var result = response.result, // 검색 결과의 컨테이너
			items = result.items; // 검색 결과의 배열
			console.log(items)
		});

		function onSuccessGeolocation(position) {
			var location = new naver.maps.LatLng(position.coords.latitude,
					position.coords.longitude);
			map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다. 
			
			var marker = new Maker({
				position : location,
				map : map
			});
			map.setCenter(location);
			map.setZoom(12);
		}
		
		function onErrorGeolocation() {
			var center = map.getCenter();
			infowindow
					.setContent('<div style="padding:20px;">'
							+ '<h5 style="margin-bottom:5px;color:#f00;">Geolocation failed!</h5>'
							+ "latitude: " + center.lat() + "<br />longitude: "
							+ center.lng() + '</div>');
			infowindow.open(map, center);
		}
	</script>
</body>
</html>