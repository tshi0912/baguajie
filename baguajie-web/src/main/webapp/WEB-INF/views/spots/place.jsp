<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:if test="${not empty place.city }">
<div class="p-15 board  mb-20">
	<div class="mb-10">
		<h4><a class="full-addr" data-city="${place.city}" data-lngLat="${place.lngLat[1]},${place.lngLat[0]}">${place.fullAddr}</a></h4>
	</div>
	<div class="p-r">
		<div id="spec_place_map" style="height:200px;"></div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		var pinyin = $('.full-addr').attr('data-city');
		var lngLat = $('.full-addr').attr('data-lngLat');
		var idx = lngLat.indexOf(',');
		var lng = parseFloat(lngLat.substring(0, idx));
		var lat = parseFloat(lngLat.substring(idx+1, lngLat.length));
		if(isNaN(lng) || isNaN(lat)){
			var no_marker = true;
		}
		$.getJSON( '<c:url value="/citymeta/" />' + pinyin, function(data){
			if(data && data.resultData){
				cityMeta = data.resultData;
				var suggestZoom = cityMeta.zoom;
				if(cityMeta.pinyin != pinyin){
					suggestZoom = 12;
				}
				if(!no_marker){
					$('#spec_place_map').gmap3({ 
						action: 'addMarker',
						latLng: new google.maps.LatLng(lng,lat),
						map:{
							center: true,
							zoom: suggestZoom,
							scrollwheel: true,
							mapTypeId: google.maps.MapTypeId.ROADMAP,
							mapTypeControl: false,
							zoomControl: true,
							zoomControlOptions: {
								style : google.maps.ZoomControlStyle.SMALL
							},
							streetViewControl: false
							}	
						}
					});
				}else{
					$('#spec_place_map').gmap3({ 
						action: 'init',
						options:{
							center: [cityMeta.lngLat[1], cityMeta.lngLat[0]],
							zoom: suggestZoom,
							scrollwheel: true,
							mapTypeId: google.maps.MapTypeId.ROADMAP,
							mapTypeControl: false,
							zoomControl: true,
							zoomControlOptions: {
								style : google.maps.ZoomControlStyle.SMALL
							},
							streetViewControl: false
						}
					});
				}
			}
		});
	});
</script>
</c:if>