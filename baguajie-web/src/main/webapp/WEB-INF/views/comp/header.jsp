<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container content-wrapper">
				<a class="btn btn-navbar" data-target=".nav-collapse" data-toggle="collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="<c:url value="/" />">八卦街
					<span class="label label-info p-0 fs-9 va-6 lh-14 ta-c">
						<span class="version">Beta</span>
						<i class="icon-home icon-white w-15 ml-5"></i>
					</span>
				</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li id="nav-profiles">
							<a href="<c:url value="/profiles" />">八哥八姐</a>
						</li>
						<li id="nav-create">
							<a href="<c:url value="/spots/create" />">我要八卦</a>
						</li>
						<li id="nav-apps">
							<a href="<c:url value="/apps" />" title="八卦街移动客户端">八卦街移动客户端</a>
						</li>
					</ul>
				<c:choose> 
					<c:when test="${empty signInUser}"> 
						<ul class="nav pull-right">
							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									关于
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="#">关于八卦街</a></li>
									<li><a href="#">八卦工具</a></li>
									<li><a href="#">开发动态</a></li>
									<li class="divider"></li>
									<li><a href="#">帮助</a></li>
									<li><a href="#">反馈</a></li>
								</ul>
							</li>
							<li class="divider-vertical"></li>
							<li><a href="#sign-in-modal" data-toggle="modal">登录</a></li>
							<li><a href="<c:url value="/signup" />">注册</a></li>
						</ul> 
					</c:when>
					<c:otherwise>
						<ul class="nav pull-right">
							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									关于
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="#">关于八卦街</a></li>
									<li><a href="#">八卦工具</a></li>
									<li><a href="#">开发动态</a></li>
									<li class="divider"></li>
									<li><a href="#">帮助</a></li>
									<li><a href="#">反馈</a></li>
								</ul>
							</li>
							<li class="divider-vertical"></li>
							<li id="nav-profile">
								<a href="<c:url value="/profiles/${signInUser.id}" />">我的门面</a></li>
							<li id="nav-dashboard">
								<a href="<c:url value="/dashboard" />">我的八卦圈</a></li>
							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									消息
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="#">查看评论</a></li>
									<li><a href="#">查看@我</a></li>
									<li><a href="#">查看私信</a></li>
									<li><a href="#">查看通知</a></li>
								</ul>
							</li>
							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									账号
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="<c:url value="/setting" />">账号设置</a></li>
									<li class="divider"></li>
									<li><a href="<c:url value="/signout" />">退出登录</a></li>
								</ul>
							</li>
						</ul>
					</c:otherwise> 
				</c:choose>
			</div>
		</div>
	</div>
	<ul id="header-hiddens" class="dis-n">
		<li><input name="signin-ts" type="hidden" value="${requestScope.signin_ts}"/></li>
	</ul>	
</div>
<c:import url="/WEB-INF/views/modal/sign.in.jsp"/>
<script type="text/javascript" src="<c:url value="/resources/languages/zh-cn/jquery.validationEngine.lang.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.validationEngine.js" />" ></script>
<script type="text/javascript">
	function adjustWebWidth(){
		var web_width = $(window).width()-50;
		var pin_h_count = Math.max(4,Math.floor((web_width - 15)/237)); 
		web_width = pin_h_count*237-15;
		$('.content-wrapper').each(function(){
			var padding_left = $(this).css('padding-left');
			var padding_right = $(this).css('padding-right');
			var padding = 0, pl, pr;
			if($.trim(padding_left).length >1){
				pl = parseFloat(padding_left.substring(0, padding_left.length-2));
				if(!isNaN(pl)){
					padding += pl;
				}
			}
			if($.trim(padding_right).length>2){
				pr = parseFloat(padding_right.substring(0, padding_right.length-2));
				if(!isNaN(pr)){
					padding += pr;
				}
			}
			$(this).css('width', web_width-padding+'px');
		});
	}
	function noNeedAdjustWebWidth(noNeed){
		if(noNeed != 'true'){
			$(document).ready(function() {
				$(window).resize(adjustWebWidth);
			});
		}
	}
	function setHeaderTab(tab){
		$('ul.nav li').removeClass('active');
		$('ul.nav li#nav-' + tab).addClass('active');
	}
	$('#geo-city-picker a').click(function(){
		var city = $(this).attr('title').toLowerCase();
		op.city_picker_geo_callback(city);
	});
	noNeedAdjustWebWidth('${param.noNeed}')
	setHeaderTab('${param.tab}');
	$('.brand').find('.icon-home').hide();
	$('.brand').mouseenter(function(){
		$(this).find('.version').hide();
		$(this).find('.icon-home').show();
	});
	$('.brand').mouseleave(function(){
		$(this).find('.version').show();
		$(this).find('.icon-home').hide();
	});
</script>