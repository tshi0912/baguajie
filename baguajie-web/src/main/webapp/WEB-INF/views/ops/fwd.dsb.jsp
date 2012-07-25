a<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<div class="cmt row-fluid">
	<div class="f-l mr-10"><a class="img" title="${fwd.createdBy.name}"
		href="<c:url value="/profiles/${fwd.createdBy.id}" />" >
		<img src="${f:avatarUrl(fwd.createdBy.avatar, fwd.createdBy.gender)}" width=30 height=30 /></a></div>
	<div class="f-l lh-16"><a href="<c:url value="/profiles/${fwd.createdBy.id}" />" >${fwd.createdBy.name}</a>&nbsp;
		<span class="c-666">${fwd.content}</span>&nbsp;
		<span class="c-888">(<span class="timeago" title="<fmt:formatDate 
		value="${fwd.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>)</span>
	</div>
</div>