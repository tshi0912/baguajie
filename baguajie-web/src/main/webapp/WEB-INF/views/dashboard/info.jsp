<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<div class="p-15 board mb-20 info">
	<div class="row-fluid">
		<a class="img f-l mr-10" href="<c:url value="/profiles/${user.id}" />" >
			<img width=100 height=100 src="${f:avatarUrl(user.avatar, user.gender)}" /></a>
		<div class="f-l">
			<h2 class="mb-7">${user.name}</h2>
			<p><a class="btn btn-error" href="#">
					<i class="icon-envelope"></i>&nbsp;帐号设置</a>
			</p>
		</div>
	</div>
	<div class="row-fluid stats ta-c bgc-gray fs-14 lh-20">
		<a class="span4 c-666" href="<c:url value="/profiles/${user.id}/spot" />">
			<strong class="dis-b fs-18 c-theme-link">${user.spotCount}</strong>
				八卦</a>
		<a class="span4 c-666" href="<c:url value="/profiles/${user.id}/follow" />">
			<strong class="dis-b fs-18 c-theme-link">${user.followCount}</strong>
				关注</a> 
		<a class="span4 c-666" href="<c:url value="/profiles/${user.id}/fan" />">
			<strong class="dis-b fs-18 c-theme-link">${user.fansCount}</strong>
				粉丝</a>
	</div>
	
</div>