<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty signInUser}"> 
<ul class="nav pull-right">
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> 关于 <b class="caret"></b>
	</a>
		<ul class="dropdown-menu">
			<li><a href="#">关于八卦街</a></li>
			<li><a href="#">八卦工具</a></li>
			<li><a href="#">开发动态</a></li>
			<li class="divider"></li>
			<li><a href="#">帮助</a></li>
			<li><a href="#">反馈</a></li>
		</ul></li>
	<li class="divider-vertical"></li>
	<li id="nav-profile"><a
		href="<c:url value="/profiles/${signInUser.id}" />">我的门面</a></li>
	<li id="nav-dashboard"><a href="<c:url value="/dashboard" />">我的八卦圈</a></li>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> 消息 <b class="caret"></b>
	</a>
		<ul class="dropdown-menu">
			<li><a href="#">查看评论</a></li>
			<li><a href="#">查看@我</a></li>
			<li><a href="#">查看私信</a></li>
			<li><a href="#">查看通知</a></li>
		</ul></li>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> 账号 <b class="caret"></b>
	</a>
		<ul class="dropdown-menu">
			<li><a href="<c:url value="/setting" />">账号设置</a></li>
			<li class="divider"></li>
			<li><a href="<c:url value="/signout" />">退出登录</a></li>
		</ul></li>
</ul>
</c:if>