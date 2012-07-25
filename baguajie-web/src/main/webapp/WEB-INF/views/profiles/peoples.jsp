<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:if test="${not empty pins}">
<c:forEach var="pin" items="${pins}">
<div class="thumbnail pin wft">
	<div class="op-view p-r">
		<a class="img" <c:if test="${pin.avatarUnset}"> title="${f:thirdPerson(pin.gender)}还没有设置头像"</c:if> 
			href="<c:url value="/profiles/${pin.userId}" />">
			<img alt="${pin.name}" width=192 height=192 src="${pin.avatarUrl}"></a>
		<div class="op-list p-a dis-n " style="top:8px; right: 2px"><ul class="ls-n ta-r">
			<li><a class="btn mb-5 btn-success follow" href="<c:url value="/ops/follow/${pin.userId}" />"
				onclick="op.change_follow(event.currentTarget); event.preventDefault();">
				<i class="icon-heart icon-white"></i>&nbsp;<span>关注Ta</span></a></li>
			<li><a class="btn" href="#">
				<i class="icon-envelope"></i>&nbsp;私信Ta</a></li>
		</ul></div>
	</div>
	<div class="caption">
		<h3><a href="<c:url value="/profiles/${pin.userId}"/>">${pin.name}</a></h3>
		<p>${pin.summary}</p>
		<p class="fs-13 c-888">
			<a href="<c:url value="/profiles/${pin.userId}/spot" />">
				<span class="fw-b fs-18">${pin.spotCount}</span></a>&nbsp;个八卦,
			<a href="<c:url value="/profiles/${pin.userId}/follow" />">
				<span class="fw-b fs-18">${pin.followCount}</span></a>&nbsp;个关注,
			<a href="<c:url value="/profiles/${pin.userId}/fan" />">
				<span id="${pin.userId}-fans-idr" class="fw-b fs-18">${pin.fansCount}</span></a>&nbsp;个粉丝
		</p>
	</div>
	<div class="convo row-fluid">
		<c:forEach var="thumb" items="${pin.spots}" varStatus="stat">
			<a class="img f-l w-60 h-60 of-h <c:if test="${stat.count%3!=0}">mr-6</c:if> mb-6 bgc-white" 
				href="<c:url value="/spots/${thumb.id}" />">
				<img width=60 src="${f:imageUrl(thumb.image.resId)}" />
			</a>
		</c:forEach>
	</div>
</div>
</c:forEach>
</c:if>