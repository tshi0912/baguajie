<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:choose>
<c:when test="${cmts != null}">
	<c:forEach var="cmt" begin="0" end="5" varStatus="stat" items="${cmts}">
	<div class="cmt row-fluid <c:if test="${stat.count == totalCmtCount}">last</c:if>">
		<div class="f-l mr-10"><a class="img" title="${cmt.createdBy.name}"
			href="<c:url value="/profiles/${cmt.createdBy.id}" />" >
			<img src="${f:avatarUrl(cmt.createdBy.avatar, cmt.createdBy.gender)}" width=30 height=30 /></a></div>
		<div class="f-l lh-16" ><a href="<c:url value="/profiles/${cmt.createdBy.id}" />" >${cmt.createdBy.name}</a>&nbsp;
			<span class="c-666">${cmt.content}</span>&nbsp;
			<span class="c-888">(<span class="timeago" title="<fmt:formatDate 
			value="${cmt.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>)</span>
		</div>
	</div>
	</c:forEach>
	<c:if test="${totalCmtCount>5}">
		<div class="cmt lh-16 last ta-r">
			<a href=""><span>还有${totalCmtCount-6}条评论</span></a>
		</div>
	</c:if>
</c:when>
<c:otherwise>
	<div class="p-8 lh-16 ta-c empty-holder">
		<span>暂时没有评论</span>
	</div>
</c:otherwise>
</c:choose>