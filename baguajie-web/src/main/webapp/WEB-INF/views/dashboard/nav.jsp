<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul id="dash-nav" class="nav nav-tabs nav-stacked mb-20 ta-c bgc-white fs-14" >
	<li class="active">
		<a href="<c:url value="/dashboard" />">动&nbsp;态</a>
	</li>
	<li class="">
		<a href="<c:url value="/dashboard/cmt" />">评&nbsp;论</a>
	</li>
	<li class="">
		<a href="#">私&nbsp;信</a>
	</li>
	<li class="">
		<a href="#">通&nbsp;知</a>
	</li>
	<li class="">
		<a href="#">@我的</a>
	</li>
</ul>