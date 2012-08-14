<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>八卦街 有图有真相</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-base.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-theme.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/js/ga.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.1.7.1.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/baguajie.init.js" />" ></script>
</head>
<body class="front">
	<jsp:include page="/WEB-INF/views/comp/header.jsp">
		<jsp:param name="tab" value="home"/>
	</jsp:include>
	<div id="water-fall-wrapper" class="main-wrapper">
		<jsp:include page="/WEB-INF/views/comp/spot.filter.bar.jsp" >
			<jsp:param name="filters" value="${filters}"/>
			<jsp:param name="viewType" value="mv"/>
		</jsp:include>
		<div id="map-view" class="board content-wrapper p-r" style="width: 1140px;">
			<div id="explore-map" style="width: 100%; height:600px;">
				<jsp:include page="/WEB-INF/views/comp/marker.tip.jsp"/>
				<div id="map-loading" class="p-a z-99 dis-n" style="top: 250px; width:100%">
					<div class="bg-black ta-c br-5 c-eee fs-16 fw-b p-15" 
						style="width:48px; height:48px; margin: 0 auto;">
						<p class="bg-big-loading-white" style="width:48px; height:48px;"></p>
					</div>
				</div>
				<div id="map-noti" class="p-a z-99 dis-n" style="top: 265px; width:100%">
					<div class="bg-black ta-c br-5 c-eee fs-16 fw-b p-15" style="width: 60%; margin: 0 auto;">更  多</div>
				</div>
				<div id="more-btn" class="p-a z-99 ta-c" style="bottom: 10px; width:100%">
					<button class="btn btn-large" style="width: 60%">更  多</button></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		adjustWebWidth();
	</script>
	<div id="page-nav">
		<a href="<c:url value="/spots/search/marker?${qStr}no=0" />"></a>
	</div>
<script type="text/javascript" src="<c:url value="/resources/js/baguajie.op.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />" ></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=zh_cn"></script>
<script type="text/javascript" src="<c:url value="/resources/js/gmap3.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/marker.overlay.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/marker.overlay.manager.js" />"></script>
<script type="text/javascript">
	$(function(){
		
		var cityMeta = null;
		
		$.getJSON( '<c:url value="/citymeta" />', function(data){
			if(data && data.resultData){
				cityMeta = data.resultData;
				$('#explore-map').gmap3({
					action: 'init',
					options:{
						noClear: true,
						center: [cityMeta.lngLat[1], cityMeta.lngLat[0]],
						zoom: cityMeta.zoom,
						scrollwheel: true,
						mapTypeId: google.maps.MapTypeId.ROADMAP,
						mapTypeControl: false,
						zoomControl: true,
						zoomControlOptions: {
							style : google.maps.ZoomControlStyle.SMALL
						},
						streetViewControl: false
					},
					callback: function(){
						MarkerOverlayManager.myMap = $(this).gmap3('get');
						getMore();
					}
				});
			}
		});
		
		$('#map-view #more-btn').click(function(){
			getMore();
		});
		
		function getMore(){
			if($('#map-loading').css('display') 
					== 'block'){
				return;
			}
			var url = $('#page-nav a').attr('href');
			if(url.lastIndexOf('/end')!=-1){
				mapNoti('没有更多了');
				return;
			}
			$('#map-loading').show();
			$.getJSON(url, function(data){
				if(data && data.resultData){
					var pinMarkers = data.resultData;
					var len = pinMarkers.length;
					if(len==0){
						mapNoti('没有更多了');
						incrPageNo(true);
					}else{
						MarkerOverlayManager.draw(pinMarkers, true);
						incrPageNo(false);
					}
				}
			})
			.complete(function(){
				$('#map-loading').hide();
			});
		}
		
		var noti_timer = null;
		
		function mapNoti(msg){
			if(noti_timer!=null){
				clearTimeout(noti_timer);
			}
			$('#map-noti div').html(msg);
			$('#map-noti').fadeIn();
			noti_timer = setTimeout(function(){
				$('#map-noti').fadeOut('slow');
			}, 1500);
		}
		
		function incrPageNo(isEnd){
			var url = $('#page-nav a').attr('href');
			var lastIdx = url.lastIndexOf('=');
			var current_no = parseFloat(url.substring(lastIdx+1, url.length));
			if(!isEnd && !isNaN(current_no)){
				$('#page-nav a').attr('href', url.substring(0, lastIdx+1) + (++current_no));
			}else if(isEnd){
				$('#page-nav a').attr('href', url.substring(0, lastIdx+1) + 'end');
			}else{
				;//nothing
			}
		}
	});
</script>
</body>
</html>