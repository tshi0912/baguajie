<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<div class="p-15 board">
	<div class="row-fluid mb-10">
		<h4 class="dis-b span8"><a href="<c:url value="/profiles/${user.id}" />">${category}</a></h4>
		<span class="dis-b span4 ta-r c-888 fs-14">共${total}个八卦</span>
	</div>
	<div class="p-r row-fluid">
		<c:forEach var="thumb" items="${thumbs}">
			<a class="img f-l w-60 h-60 of-h mr-10 mb-10" 
				href="<c:url value="/spots/${thumb.id}" />">
				<img width=60 src="${f:imageUrl(thumb.image.resId)}" />
			</a>
		</c:forEach>
	</div>
</div>