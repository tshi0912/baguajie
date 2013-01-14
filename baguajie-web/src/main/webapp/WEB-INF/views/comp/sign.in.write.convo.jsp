<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:if test="${not empty signInUser}">
<div class="write convo row-fluid dis-n">
	<a class="img f-l" title="${signInUser.name}"
		href="<c:url value="/profiles/${signInUser.id}" />">
		<img width=30 height=30
		src="${f:avatarUrl(signInUser.avatar,signInUser.gender)}">
	</a>
	<form method="POST" class="act-cmt p-r mb-0" style="height: 32px;"
		action="<c:url value="/ops/cmt/create" />"
		data-cmt-url="<c:url value="/ops/cmt/create" />"
		data-fwd-url="<c:url value="/ops/forward/create" />">
		<input type="hidden" name="actId" value="" />
		<textarea class="mb-0" placeholder="添加评论..."
			registered-at="registered" autocomplete="off" name="content"></textarea>
		<a class="grid_comment_button" onclick="return false;" href="#"></a>
		<span class="grid_comment_loading dis-n"></span>
	</form>
</div>
</c:if>