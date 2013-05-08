<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<c:if test="${activities != null}">
	<c:forEach var="activity" varStatus="stat" items="${activities}">
		<dl class="act-list row-fluid">
			<dt class="face">
				<a class="img f-l w-60 h-60 of-h"
					title = "${activity.targetUser.name}" 
					href="<c:url value="/profiles/${activity.targetUser.id}"/>" >
					<img width=60 src="${f:avatarUrl(activity.targetUser.avatar, activity.targetUser.gender)}" />
				</a>
			</dt>
			<dd class="content">
				<c:choose>
				<c:when test="${activity.type == 'SPOT'}">
					<p class="">
						<a href="<c:url value="/profiles/${activity.owner.id}" />">
							${activity.owner.name}</a>
						添加新八卦:&nbsp;
						<a href="<c:url value="/spots/${activity.targetSpot.id}" />">
							${activity.targetSpot.name}@${activity.targetSpot.place.fullAddr}</a></p>
					<p>${activity.targetSpot.summary}</p>
					<p class="">
						<img class="" width="100" src="${f:imageUrl(activity.targetSpot.image.resId)}">
					</p>
					<p class="mb-0 row-fluid">
						<span class="timeago f-l" 
							title="<fmt:formatDate value="${activity.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>
						<span class="f-r">
							<a onclick="op.toggle_fwd_dashboard(event.currentTarget); event.preventDefault();">
								转发(${activity.forwardedCount})</a>
						   	<i class="mr-5 ml-5 c-aaa">|</i>
						   	<a onclick="op.toggle_cmt_dashboard(event.currentTarget); event.preventDefault();">
						   		评论(${activity.commentedCount})</a>
						</span>
					</p>
				</c:when>
				<c:when test="${activity.type == 'FORWARD'}">
					<p class="mb-15">
						<a href="<c:url value="/profiles/${activity.owner.id}" />">
							${activity.owner.name}</a>
						${activity.content}</p>
					<c:if test="${not empty activity.targetSpot}">
					<dl class="target">
						<dd class="arrow bgcolor_arrow">
							<em>◆</em><span>◆</span></dd>
						<dt>
							<a href="/profiles/<c:out value="${activity.targetSpot.createdBy.id}"/>">@${activity.targetSpot.createdBy.name}</a>&nbsp;
							<a href="/spots/<c:out value="${activity.targetSpot.id}"/>">${activity.targetSpot.name} @ 
								${activity.targetSpot.place.fullAddr}</a><br/>
							<span>${activity.targetSpot.summary}</span>
						</dt>
						<dd class="ml-0"><div class="">
							<img class="" width="100" src="${f:imageUrl(activity.targetSpot.image.resId)}">
						</div></dd>
						<dd class="ml-0 mt-8 row-fluid">
							<span class="f-l">
								<fmt:formatDate value="${activity.targetSpot.createdAt}" 
									pattern="MM月dd日 HH:mm:ss"/></span>
							<span class="f-r">
								<a href="<c:url value="/spots/${activity.targetSpot.id}"/>">跟踪(${activity.targetSpot.trackedCount})</a>
								<i class="mr-5 ml-5 c-aaa">|</i>
							   	<a href="<c:url value="/spots/${activity.targetSpot.id}"/>" href="">转发(${activity.targetSpot.forwardedCount})</a>
							   	<i class="mr-5 ml-5 c-aaa">|</i>
							   	<a href="<c:url value="/spots/${activity.targetSpot.id}"/>">评论(${activity.targetSpot.commentedCount})</a>
							</span>
						</dd>
					</dl>
					</c:if>
					<p class="mb-0 row-fluid">
						<span class="timeago f-l" 
							title="<fmt:formatDate value="${activity.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>
						<span class="f-r">
							<a onclick="op.toggle_fwd_dashboard(event.currentTarget); event.preventDefault();">
								转发(${activity.forwardedCount})</a>
						   	<i class="mr-5 ml-5 c-aaa">|</i>
						   	<a onclick="op.toggle_cmt_dashboard(event.currentTarget); event.preventDefault();">
						   		评论(${activity.commentedCount})</a>
						</span>
					</p>
				</c:when>
				<c:when test="${activity.type == 'COMMENT'}">
					<p>
						<a href="<c:url value="/profiles/${activity.owner.id}" />">
							${activity.owner.name}</a>
						评论: ${activity.content}</p>
					<p><c:if test="${not empty activity.basedOn}">
						<a href="<c:url value="/profiles/${activity.basedOn.owner.id}" /> ">@${activity.basedOn.owner.name}</a>:
						<span>${activity.basedOn.content}</span>
					</c:if>
					<c:if test="${not empty activity.targetSpot}">
						<a href="<c:url value="/spots/${activity.targetSpot.id}" /> ">${activity.targetSpot.name} @ 
								${activity.targetSpot.place.fullAddr}</a><br/>
						<span>${activity.targetSpot.summary}</span>
					</c:if>
					</p>
					<p class="mb-0 row-fluid">
						<span class="timeago f-l" 
							title="<fmt:formatDate value="${activity.createdAt}" pattern="yyyy-MM-dd HH:mm:ss Z"/>"></span>
						<c:if test="${sessionScope.signInUser.id != activity.owner.id}">
						<span class="f-r">
							<a onclick="op.toggle_rep_dashboard(event.currentTarget); event.preventDefault();">
								回复</a>
						</span>
						</c:if>
					</p>
				</c:when>
				</c:choose>
				
				<c:choose>
				<c:when test="${activity.type == 'SPOT' || activity.type == 'FORWARD'}">
				<div class="cmts-box repeat dis-n">
					<div class="arrow bgcolor_arrow">
							<em>◆</em><span>◆</span></div>
					<div class="write row-fluid">
						<a class="img f-l" title="${sessionScope.signInUser.name}" 
							href="<c:url value="/profiles/${sessionScope.signInUser.id}" />">
							<img width=30 height=30 src="${f:avatarUrl(sessionScope.signInUser.avatar,sessionScope.signInUser.gender)}">
						</a>
						<form method="POST" class="cmt dsb p-r ml-85 mb-0" style="height:32px;" 
							action="<c:url value="/ops/cmt/create" />" >
							<input type="hidden" name="actId" value="${activity.id}" />
							<textarea class="mb-0" placeholder="添加评论..." 
								autocomplete="off"
								name="content"></textarea>
							<a class="grid_comment_button" onclick="return false;" href="#"></a>
							<span class="grid_comment_loading dis-n"></span>
						</form>
					</div>
					<div class="cmts">
						<div class="p-8 convo lh-16 ta-c loading-box">
							<a href="" class="bg-h-loading pl-85 c-888" >加载评论...</a>
						</div>
					</div>
				</div>
				<div class="fwds-box repeat dis-n">
					<div class="arrow bgcolor_arrow">
							<em>◆</em><span>◆</span></div>
					<c:choose>
					<c:when test="${signInUser.id != activity.owner.id}">
					<div class="write row-fluid">
						<a class="img f-l" title="${sessionScope.signInUser.name}" 
							href="<c:url value="/profiles/${sessionScope.signInUser.id}" />">
							<img width=30 height=30 src="${f:avatarUrl(sessionScope.signInUser.avatar,sessionScope.signInUser.gender)}">
						</a>
						<form method="POST" class="fwd dsb p-r ml-85 mb-0" style="height:32px;" 
							action="<c:url value="/ops/forward/create" />" >
							<input type="hidden" name="actId" value="${activity.id}" />
							<textarea class="mb-0" placeholder="转发八卦..." 
								autocomplete="off" 
								<c:choose>
								<c:when test="${activity.type == 'FORWARD'}">
								data-based-on="//@${activity.owner.name}: ${activity.content}"
								</c:when>
								<c:when test="${activity.type == 'SPOT'}">
								data-based-on="转发八卦: "
								</c:when>
								</c:choose>
								name="content"></textarea>
							<a class="grid_comment_button" onclick="return false;" href="#"></a>
							<span class="grid_comment_loading dis-n"></span>
						</form>
					</div>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="actId" value="${activity.id}" />
					</c:otherwise>
					</c:choose>
					<div class="cmts">
						<div class="p-8 convo lh-16 ta-c loading-box">
							<a href="" class="bg-h-loading pl-85 c-888" >加载转发...</a>
						</div>
					</div>
				</div>
				</c:when>
				<c:when test="${activity.type == 'COMMENT' &&
					signInUser.id != activity.owner.id}">
				<div class="reps-box repeat dis-n">
					<div class="arrow bgcolor_arrow">
							<em>◆</em><span>◆</span></div>
					<div class="write row-fluid">
						<a class="img f-l" title="${sessionScope.signInUser.name}" 
							href="<c:url value="/profiles/${sessionScope.signInUser.id}" />">
							<img width=30 height=30 src="${f:avatarUrl(sessionScope.signInUser.avatar,sessionScope.signInUser.gender)}">
						</a>
						<form method="POST" class="rep dsb p-r ml-85 mb-0" style="height:32px;" 
							action="<c:url value="/ops/cmt/create" />" >
							<input type="hidden" name="actId" value="${activity.basedOn.id}" />
							<textarea class="mb-0" placeholder="回复Ta..." 
								autocomplete="off"
								data-based-on="@${activity.owner.name}: "
								name="content"></textarea>
							<a class="grid_comment_button" onclick="return false;" href="#"></a>
							<span class="grid_comment_loading dis-n"></span>
						</form>
					</div>
					<div class="cmts">
						<div class="p-8 convo lh-16 ta-c loading-box">
							<a href="" class="bg-h-loading pl-85 c-888" >加载对话...</a>
						</div>
					</div>
				</div>
				</c:when>
				</c:choose>
			</dd>
		</dl>
	</c:forEach>
</c:if>