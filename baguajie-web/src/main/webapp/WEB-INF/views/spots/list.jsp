<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:if test="${not empty pins}">
<c:forEach var="pin" items="${pins}">
<div class="thumbnail pin wft" data-act-id="${pin.actId}">
	<div class="op-view p-r">
		<c:choose>
		<c:when test="${pin.imageHeight > 800}">
			<a class="img long" href="<c:url value="/spots/${pin.spotId}" />">
			<img alt="<c:out value="${pin.name}"/>" width=192 height=${pin.imageHeight} src="${pin.imageUrl}">
			<span class="stop"></span></a>
		</c:when>
		<c:otherwise>
			<a class="img" href="<c:url value="/spots/${pin.spotId}" />">
			<img alt="<c:out value="${pin.name}"/>" width=192 height=${pin.imageHeight} src="${pin.imageUrl}"></a>
		</c:otherwise>
		</c:choose>
		<div class="op-list p-a dis-n " style="top:8px; right: 2px"><ul class="ls-n ta-r">
			<li><a class="btn mb-5 btn-success track" href="<c:url value="/ops/track/${pin.spotId}" />"
				onclick="op.change_track(event.currentTarget); event.preventDefault();">
				<i class="icon-heart icon-white"></i>&nbsp;<span>追踪</span></a></li>
			<li><a class="btn mb-5" href="javascript:void(0);"
				onclick="op.show_forward(event.currentTarget); event.preventDefault();">
				<i class="icon-share-alt"></i>&nbsp;<span>转发</span></a></li>
			<li><a class="btn" href="javascript:void(0);"
				onclick="op.show_comment(event.currentTarget); event.preventDefault();">
				<i class="icon-comment"></i>&nbsp;<span>评论</span></a></li>
		</ul></div>
	</div>
	<div class="caption">
		<p><a href="#" data-id="<c:out value="${pin.placeId}"/>" data-type="placemap" 
			data-html="true" data-toggle="popoverx" data-original-title="详细地图"
			data-city="${pin.city}" data-lngLat="${pin.lngLat[1]},${pin.lngLat[0]}">
			<i class="icon-map-marker"></i>${pin.placeAddr}</a></p>
		<h5><c:out value="${pin.name}"/></h5>
		<p>${pin.summary}</p>
	</div>
	<div class="convo clear">
		<p class="clear mb-0">
			<a class="img f-l mr-5 mb-5" href="<c:url value="/profiles/${pin.createdById}" />"
				data-id="${pin.createdById}" data-type="namecard" data-original-title="用户信息"
				data-html="true" data-trigger="manual" data-toggle="popoverx">
				<img src="${pin.createdByAvatarUrl}" width=30 height=30 /></a>
			<a class="" href="<c:url value="/profiles/${pin.createdById}" />" >${pin.createdByName}</a>
			&nbsp;添加此八卦到&nbsp;
			<a href="<c:url value="/?pinyin=quanguo&category=${pin.category}" />">${pin.category}</a>
			<span>(<span class="timeago" title="<fmt:formatDate 
				value="${pin.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>)</span>
		</p>
	</div>
	<c:if test="${not empty sessionScope.signInUser}">
	<div class="write convo row-fluid dis-n">
		<a class="img f-l" title="${sessionScope.signInUser.name}" 
			href="<c:url value="/profiles/${sessionScope.signInUser.id}" />">
			<img width=30 height=30 src="${f:avatarUrl(sessionScope.signInUser.avatar,sessionScope.signInUser.gender)}">
		</a>
		<form method="POST" class="act-cmt p-r mb-0" style="height:32px;" 
			action="<c:url value="/ops/cmt/create" />" 
			data-cmt-url="<c:url value="/ops/cmt/create" />"
			data-fwd-url="<c:url value="/ops/forward/create" />">
			<input type="hidden" name="actId" value="${pin.actId}" />
			<textarea class="mb-0" placeholder="添加评论..." 
				registered-at="registered" autocomplete="off"
				name="content"></textarea>
			<a class="grid_comment_button" onclick="return false;" href="#"></a>
			<span class="grid_comment_loading dis-n"></span>
		</form>
	</div>
	</c:if>
	<div class="cmts <c:if test="${empty pin.cmts}">dis-n</c:if>">
		<c:if test="${not empty pin.cmts}">
			<c:forEach var="cmt" begin="0" end="5" items="${pin.cmts}">
			<div class="cmt convo row-fluid">
				<div class="f-l mr-5">
					<a class="img dis-b" title="${cmt.createdBy.name}" data-original-title="用户信息"
						data-id="${cmt.createdBy.id}" data-type="namecard"
						data-html="true" data-trigger="manual" data-toggle="popoverx"
						href="<c:url value="/profiles/${cmt.createdBy.id}" />" >
						<img src="${f:avatarUrl(cmt.createdBy.avatar, cmt.createdBy.gender)}" width=30 height=30 /></a></div>
				<div class="f-l lh-16" style="width: 157px;"><a href="<c:url value="/profiles/${pin.createdById}" />" >${cmt.createdBy.name}</a>&nbsp;
					<span class="c-666">${cmt.content}</span>&nbsp;
					<span>(<span class="timeago" title="<fmt:formatDate 
					value="${cmt.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>)</span>
				</div>
			</div>
			</c:forEach>
			<c:if test="${pin.totalCmtCount>6}">
				<div class="cmt convo lh-16 ta-r">
					<a href=""><span>还有${pin.totalCmtCount-6}条评论</span></a>
				</div>
			</c:if>
		</c:if>
	</div>
</div>
</c:forEach>
</c:if>
