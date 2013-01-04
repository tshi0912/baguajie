<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>${user.name} 八卦街 有图有真相</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-base.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-theme.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/validationEngine.bootstrap.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.1.7.1.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/baguajie.init.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/baguajie.op.js" />" ></script>
</head>
<body class="front">
	<c:choose>
		<c:when test="${signInUser.id == user.id}">
			<jsp:include page="/WEB-INF/views/comp/header.jsp">
				<jsp:param name="tab" value="profile"/>
			</jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="/WEB-INF/views/comp/header.jsp">
				<jsp:param name="tab" value="none"/>
			</jsp:include>
		</c:otherwise>
	</c:choose>
	<div class="main-wrapper mb-30">
		<div class="main block-h-c content-wrapper row-fluid p-r">
			<div class="span8 board">
				<div class="pl-30 pr-30 pt-20">
				    <ul id="profile-nav-tabs" class="nav nav-tabs fs-14" 
				    	style="margin-left: -30px; margin-right: -30px;  margin-bottom:0px">
				    	<c:choose>
				    	<c:when test="${viewUser}">
				    		<li class="fan <c:if test="${view eq 'fan'}">active</c:if>"  style="margin-left:30px">
				   				<a href="<c:url value="/profiles/${user.id}/fan"/>">粉丝( ${user.fansCount} )</a></li>
				    		<li class="follow <c:if test="${view eq 'follow'}">active</c:if>">
				    			<a href="<c:url value="/profiles/${user.id}/follow"/>">关注( ${user.followCount} )</a></li>
				    	</c:when>
				    	<c:otherwise>
				    		<li class="spot <c:if test="${view eq 'spot'}">active</c:if>"  style="margin-left:30px">
				   				<a href="<c:url value="/profiles/${user.id}/spot"/>">八卦( ${user.spotCount} )</a></li>
				    		<li class="track <c:if test="${view eq 'track'}">active</c:if>">
				    			<a href="<c:url value="/profiles/${user.id}/track"/>">追踪( ${user.trackCount} )</a></li>
				    	</c:otherwise>
				    	</c:choose>
				    </ul>
			    </div>
			    <div class="tab-content bg-gray p-20" id="water-fall-wrapper" >
				    <div class="tab-pane active" id="water-fall">
				    	<c:import url="/profiles/${user.id}/${view}s/0"></c:import>
				    </div>
				</div>
			    <div id="page-nav">
					<a href="<c:url value="/profiles/${user.id}/${view}s/1" />"></a>
				</div>
			</div>
			<div class="span4">
				<c:import url="/profiles/${user.id}/basic"></c:import>
				<c:import url="/profiles/activity/${user.id}/0"></c:import>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		adjustWebWidth();
	</script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrapx-clickover.js" />" ></script>
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
		$('.pin').each(function(){
			op.pin_bind_event($(this));
		});

		var $wf = $('#water-fall');
			
		$wf.masonry({
			itemSelector : '.pin',
		    columnWidth : 222,
		    gutterWidth: 15,
		    isAnimated: false,
		    animationOptions: {
		    	queue: false
		    },
		    isFitWidth: true
		});
			
		$wf.infinitescroll(
			{
				navSelector  : '#page-nav', // selector for the paged navigation
				nextSelector : '#page-nav a', // selector for the NEXT link (to page 2)
				itemSelector : '.pin', // selector for all items you'll retrieve
				debug        : false,
				animate	 	 : false,
				animationOptions: {
				    duration: 750,
				    easing: 'linear',
				    queue: false
				},
				loading: {
					selector: '#water-fall-wrapper',
					finishedMsg: '没有更多了',
					msgText: '八卦加载中...',
					img: '<c:url value="/resources/img/big-loading.gif" />',
					speed: 0
				},
				state : {
					currPage: 0
				},
				pathParse: function() {
			        return ['<c:url value="/profiles/${user.id}/${view}s/" />', ''];
			    }
			},
			// trigger Masonry as a callback
			function( newElements ) {
				// hide new items while they are loading
				var $newElems = $( newElements ).hide();
				$newElems.each(function(){
					op.pin_bind_event($(this));
				});
				$newElems.find(".timeago").timeago();
				// ensure that images load before adding to masonry layout
				//$newElems.imagesLoaded(function(){
					// show elems now they're ready
					$wf.append( $newElems ).masonry( 'appended', 
							$newElems, false, function(){
						$newElems.fadeIn('slow');
					});
				//}); 
			}
		);
	});
</script>
<script type="text/javascript" src="<c:url value="/resources/js/ga.js" />" ></script>
</body>
</html>