<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>八卦街 有图有真相</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-base.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-theme.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.1.7.1.js" />" ></script>
</head>
<body class="front">
	<jsp:include page="/WEB-INF/views/comp/header.jsp">
		<jsp:param name="tab" value="none"/>
	</jsp:include>
	<div class="main-wrapper mb-30">
		<div class="main block-h-c content-wrapper row-fluid p-r">
			<div class="span8 board">
				<div class="pl-30 pr-30 pb-20 pt-20 bb-divider clear">
					<a class="img mr-10 f-l" href="<c:url value="/profiles/${spot.createdBy.id}" />">
						<img width=60 height=60
							src="${f:avatarUrl(spot.createdBy.avatar, spot.createdBy.gender)}" /></a>
					<p class="fs-20"><a href="<c:url value="/profiles/${spot.createdBy.id}" />">${spot.createdBy.name}</a></p>
					<p class="fs-14 mb-0">
						<span class="timeago" 
							title="<fmt:formatDate value="${spot.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>
						添加此八卦到&nbsp;
						<a class="" >${spot.category}</a>
					</p>
				</div>
				<div class="pl-30 pr-30 pb-20 pt-20">
					<a class="btn btn-success mr-5" href="#">
						<i class="icon-heart icon-white"></i>&nbsp;追踪
					</a>
					<a class="btn" href="#">
						<i class="icon-share-alt"></i>&nbsp;转发
					</a>
				</div>
				<div class="pl-30 pr-30 pb-20 p-r">
					<a class="img" href="#">
						<img class="block-h-c" width="${spot.image.orgSize[1]}" height="${spot.image.orgSize[0]}" 
							src="${f:imageUrl(spot.image.resId)}" alt="${spot.name}"/>
					</a>
				</div>
				<div class="pl-30 pr-30 pb-20 p-r">
					<c:if test="${not empty spot.name}">
						<p class="fs-16 fw-b">${spot.name}</p>
					</c:if>
					<c:if test="${not empty spot.summary}">
						<p class="fs-14">${spot.summary}</p>
					</c:if>
				</div>
			</div>
			<div class="span4">
				<jsp:include page="/WEB-INF/views/spots/place.jsp">
					<jsp:param name="place" value="${spot.place}"/>
					<jsp:param name="pinyin" value="${spot.city}"/>
				</jsp:include>
				<c:import url="/categories/thumb">
					<c:param name="uid" value="${spot.createdBy.id}"/>
					<c:param name="category" value="${spot.category}"/>
				</c:import>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		adjustWebWidth();
	</script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.timeago.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui-1.8.18.custom.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.scrollTo.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.form.js" />"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=zh_cn"></script>
<script type="text/javascript" src="<c:url value="/resources/js/gmap3.js" />"></script>
<script type="text/javascript">
	$(function(){
		 $(".timeago").timeago();
	});
</script>
</body>
</html>