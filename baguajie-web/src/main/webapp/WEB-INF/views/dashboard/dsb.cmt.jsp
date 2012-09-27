<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>评论 ${user.name}的八卦圈 八卦街 有图有真相</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-base.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-theme.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/validationEngine.bootstrap.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/js/ga.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.1.7.1.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/baguajie.op.js" />" ></script>
</head>
<body class="front">
	<jsp:include page="/WEB-INF/views/comp/header.jsp">
		<jsp:param name="tab" value="dashboard"/>
	</jsp:include>
	<div class="main-wrapper mb-30">
		<div class="main block-h-c content-wrapper row-fluid p-r">
			<div class="span8 board">
				<div class="pl-30 pr-30 pt-20">
				    <ul id="profile-nav-tabs" class="nav nav-tabs fs-14" 
				    	style="margin-left: -30px; margin-right: -30px;  margin-bottom:0px">
			    		<li class="cmt-me active" style="margin-left:30px;">
			    			<a data-toggle="tab" href="#cmt-me-act-list">评论我的</a></li>
			    		<li class="my-cmt">
			    			<a data-toggle="tab" href="#my-cmt-act-list" 
			    				data-action="<c:url value="/activities/${user.id}/cmtfrom/0"/>">我的评论</a></li>
				    </ul>
			    </div>
			    <div id="act-list-wrapper"  class="tab-content bg-white p-20" >
				    <div id="cmt-me-act-list" class="tab-pane active">
				    	<c:import url="/activities/${user.id}/cmtbased/0"></c:import>
				    </div>
				    <div id="my-cmt-act-list" class="tab-pane">
				    	<div class="p-8 lh-16 ta-c loading-box">
				    		<a href="" class="bg-h-loading pl-85 c-888" >加载中...</a>
				    	</div>
				    </div>
				</div>
			    <div id="cmt-me-page-nav">
					<a href="<c:url value="/activities/${user.id}/cmtbased/1" />"></a>
				</div>
				<div id="my-cmt-page-nav">
					<a href="<c:url value="/activities/${user.id}/cmtfrom/1" />"></a>
				</div>
			</div>
			<div class="span4">
				<jsp:include page="/WEB-INF/views/dashboard/info.jsp"/>
				<jsp:include page="/WEB-INF/views/dashboard/nav.jsp"/>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		adjustWebWidth();
	</script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.timeago.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui-1.8.18.custom.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.scrollTo.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.form.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.imagesloaded.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.masonry.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.infinitescroll.js" />" ></script>
<script type="text/javascript">
	$(function(){
		$(".timeago").timeago();
		$('.act-list').each(function(){
			op.act_bind_event($(this));
		});
		
		$('#profile-nav-tabs .my-cmt a').click(function(){
			var $this = $(this);
			if(! $(this).data('first-load')){
				var data_action = $(this).attr('data-action');
				var c = $('#my-cmt-act-list');
				var loading_box = c.find('.loading-box');
				$.ajax({
					url: data_action,
					beforeSend: function(){
						loading_box.show();
					},
					success: function(data) {
						if($.trim(data)){
							var act = $(data).prependTo(c);
							act.each(function(){
								op.act_bind_event($(this));
							});
							act.find('.timeago').timeago();
						}
					},
					complete: function(){
						loading_box.hide();
						$this.data('first-load', true);
					}
				});
			}
		});
			
		var $cmal = $('#cmt-me-act-list');
		var $mcal = $('#my-cmt-act-list');
		
		$cmal.infinitescroll(
			{
				navSelector  : '#cmt-me-page-nav', // selector for the paged navigation
				nextSelector : '#cmt-me-page-nav a', // selector for the NEXT link (to page 2)
				itemSelector : '.act-list', // selector for all items you'll retrieve
				debug        : false,
				animate	 	 : false,
				animationOptions: {
				    duration: 750,
				    easing: 'linear',
				    queue: false
				},
				loading: {
					selector: '#act-list-wrapper',
					finishedMsg: '没有更多了',
					msgText: '动态加载中...',
					img: '<c:url value="/resources/img/big-loading.gif" />',
					speed: 0
				},
				state : {
					currPage: 0
				},
				pathParse: function() {
			        return ['<c:url value="/activities/${user.id}/cmtbased/" />', ''];
			    }
			},
			function( newElements ) {
				var $newElems = $( newElements );
				$newElems.find(".timeago").timeago();
				$newElems.each(function(){
					op.act_bind_event($(this));
				});
				$fal.append( $newElems );
			}
		);
		$mcal.infinitescroll(
				{
					navSelector  : '#my-cmt-page-nav', // selector for the paged navigation
					nextSelector : '#my-cmt-page-nav a', // selector for the NEXT link (to page 2)
					itemSelector : '.act-list', // selector for all items you'll retrieve
					debug        : false,
					animate	 	 : false,
					animationOptions: {
					    duration: 750,
					    easing: 'linear',
					    queue: false
					},
					loading: {
						selector: '#act-list-wrapper',
						finishedMsg: '没有更多了',
						msgText: '动态加载中...',
						img: '<c:url value="/resources/img/big-loading.gif" />',
						speed: 0
					},
					state : {
						currPage: 0
					},
					pathParse: function() {
				        return ['<c:url value="/activities/${user.id}/cmtfrom/" />', ''];
				    }
				},
				function( newElements ) {
					var $newElems = $( newElements );
					$newElems.find(".timeago").timeago();
					$newElems.each(function(){
						op.act_bind_event($(this));
					});
					$tal.append( $newElems );
				}
			);
	});
</script>
</body>
</html>