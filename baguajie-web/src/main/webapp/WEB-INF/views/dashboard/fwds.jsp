<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:choose>
<c:when test="${fwds != null}">
	<c:forEach var="fwd" begin="0" end="5" varStatus="stat" items="${fwds}">
	<div class="cmt row-fluid <c:if test="${stat.count == totalFwdCount}">last</c:if>">
		<div class="f-l mr-10"><a class="img" title="${fwd.createdBy.name}"
			href="<c:url value="/profiles/${fwd.createdBy.id}" />" >
			<img src="${f:avatarUrl(fwd.createdBy.avatar, fwd.createdBy.gender)}" width=30 height=30 /></a></div>
		<div class="f-l lh-16" ><a href="<c:url value="/profiles/${fwd.createdBy.id}" />" >${fwd.createdBy.name}</a>&nbsp;
			<span class="c-666">${fwd.content}</span>&nbsp;
			<span class="c-888">(<span class="timeago" title="<fmt:formatDate 
			value="${fwd.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>)</span>
		</div>
	</div>
	</c:forEach>
	<c:if test="${totalFwdCount>5}">
		<div class="cmt lh-16 last ta-r">
			<a href=""><span>还有${totalFwdCount-6}条转发</span></a>
		</div>
	</c:if>
</c:when>
<c:otherwise>
	<div class="p-8 lh-16 ta-c empty-holder">
		<span>暂时没有转发</span>
	</div>
</c:otherwise>
</c:choose>