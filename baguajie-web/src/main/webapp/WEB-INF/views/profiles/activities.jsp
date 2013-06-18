<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:if test="${activities != null}">
<div class="p-15 board mb-20">
	<c:forEach var="activity" varStatus="stat" items="${activities}">
		<div class="row-fluid
			<c:if test="${!stat.last}">
				bb-divider mb-10
			</c:if> 
			">
			<c:choose>
				<c:when test="${activity.targetSpot!=null}">
				<a class="img f-l w-60 h-60 of-h mr-10 mb-10"
					title="<c:out value="${activity.targetSpot.name}"/>" 
					href="<c:url value="/spots/${activity.targetSpot.id}"/>" >
					<img width=60 src="${f:imageUrl(activity.targetSpot.image.resId)}" />
				</a>
				</c:when>
				<c:otherwise>
				<a class="img f-l w-60 h-60 of-h mr-10 mb-10"
					title = "${activity.targetUser.name}" 
					href="<c:url value="/profiles/${activity.targetUser.id}"/>" >
					<img width=60 src="${f:avatarUrl(activity.targetUser.avatar, activity.targetUser.gender)}" />
				</a>
				</c:otherwise>
			</c:choose>
			${f:activity(activity)}&nbsp;
			<span class="c-999">(<span class="timeago" title="<fmt:formatDate 
				value="${activity.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>)</span>
		</div>
	</c:forEach>
</div>
<script type="text/javascript">
	$(function(){
		$('.timeago').timeago();
	});
</script>
</c:if>