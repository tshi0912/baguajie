<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<div class="board" style="height:150px">
	<div class="pl-10 pt-10 pr-10 pb-5 row-fluid" style="height:97px">
		<a class="img f-l mr-10" href="<c:url value="/profiles/${user.id}" />" >
			<img width=60 height=60 src="${f:avatarUrl(user.avatar, user.gender)}" /></a>
		<div class="f-l">
			<h3>${user.name}</h3>
			<p class="fs-14 lh-25 c-888">
				<a href="<c:url value="/profiles/${user.id}/spot" />">
					<span class="fw-b fs-20">${user.spotCount}</span></a>&nbsp;八卦,
				<a href="<c:url value="/profiles/${user.id}/follow" />">
					<span class="fw-b fs-20">${user.followCount}</span></a>&nbsp;关注, 
				<a href="<c:url value="/profiles/${user.id}/fan" />">
					<span id="fans-idr" class="fw-b fs-20">${user.fansCount}</span></a>&nbsp;粉丝
			</p>
			<p class="fs-14 lh-20 c-888">
				<c:if test="${not empty user.city}">
					城市: <span class="c-333">${user.city}</span>&nbsp;
				</c:if>
				<c:if test="${user.gender != null}">
					性别: <span class="c-333">${f:gender(user.gender)}</span>
				</c:if>
			</p>
			<p class="fs-14 lh-20 c-888">
				<c:choose>
					<c:when test="${not empty user.summary}">
						${user.summary}
					</c:when>
					<c:otherwise>
						 这家伙很懒 什么都没留下
					</c:otherwise>
				</c:choose>
			</p>
		</div>
	</div>
	<div class="pb-5 pt-5 row-fluid bgc-gray" style="height:28px">
		<div class="f-l" style="margin-left:80px">
			<p><a class="btn btn-success follow" href="<c:url value="/ops/follow/${user.id}" />" 
					onclick="op.change_follow(event.currentTarget); event.preventDefault();" data-idr="#fans-idr">
					<i class="icon-eye-open icon-white"></i>&nbsp;<span>关注Ta</span></a>
				<a class="btn" href="#">
					<i class="icon-envelope"></i>&nbsp;私信Ta</a>
			</p>
		</div>
	</div>
</div>