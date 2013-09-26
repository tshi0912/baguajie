<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>我要八卦 八卦街 有图有真相</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-base.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-theme.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/uploadify.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/js/ga.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.1.7.1.js" />" ></script>
</head>
<body>
	<div class="container">
		<jsp:include page="/WEB-INF/views/comp/header.jsp">
			<jsp:param name="tab" value="create"/>
		</jsp:include>
		<div class="row mt-30" >
			<div class="span10 offset1 mt-80">
				<div class="hero-unit board">
					<h1>我要八卦</h1>
					<p>从本地上传或引用网络图片进行八卦分享</p>
					<div class="row-fluid">
						<div class="span5 row-fluid">
							<div class="span11 thumbnail row-fluid">
								<div class="span12 upload-img p-r row-fluid">
									<a id="upload-image-btn" class="p-a upload-btn" 
										href="#upload-image-modal" data-toggle="modal" title="上传,引用图片">+</a>
									<img id="spot-image" class="" alt="" width=244 src="http://placehold.it/245&text=Upload+Image">
								</div>
							</div>
						</div>
						<div class="span7">
							<form:form id="create-spot-form" cssClass="sign-in" modelAttribute="signUpUserVo" method="post">
								<input id="image-url-hid" name="imageUrl" type="hidden" />
								<input id="place-id-hid" name="placeId" type="hidden" />
								<div class="row-fluid">
									<input class="span9" id="name" name="name" type="text" 
											placeholder="给八卦取个名称" autocomplete="off" value="<c:out value="${signUpUserVo.email}" />" />
									<div class="span2 offset1"><form:errors path="name" cssClass="alert alert-error" /></div>
								</div>
								<div class="row-fluid mt-10">
									<div class="input-append">
										<input id="full-addr-input" name="fullAddr" type="text" class="span3"
												placeholder="八卦发生地点" autocomplete="off" value="<c:out value="${signUpUserVo.name}" />" />
										<span class="add-on">
											<a id="place-locate-btn" class="" 
													href="#place-locate-modal" data-toggle="modal" title="手动定位地址">
											<i class="icon-map-marker"></i></a></span>
									</div>
									<div class="span2 offset1"><form:errors path="fullAddr" cssClass="alert alert-error"/></div>
								</div>
								<div class="row-fluid mt-10">
									<select id="category" class="span5" name="category">
											<option>选择分类...</option>
											<option>新闻</option>
											<option>美食</option>
											<option>美图</option>
											<option>娱乐</option>
											<option>购物</option>
									</select>
									<div class="span2 offset1"><form:errors path="password" cssClass="alert alert-error"/></div>
								</div>
								<div class="row-fluid mt-10">
									<textarea id="summary" class="span9" name="summary"
											placeholder="随便描述下..." autocomplete="off" ></textarea>
									<div class="span2 offset1"><form:errors path="summary" cssClass="alert alert-error"/></div>
								</div>
								<div class="row-fluid mt-10">
									 <button type="submit" class="btn btn-primary btn-large">八卦一下</button>
									 <a id="return-btn" class="btn btn-large ml-10" href="<c:url value="/" />">返回</a>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			adjustWebWidth();
		</script>
	</div>
<c:import url="../modal/upload.jsp"/>
<c:import url="../modal/locate.jsp"/>
<script type="text/javascript" src="<c:url value="/resources/js/swfobject.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.uploadify.v2.1.4.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.form.js" />"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=zh_cn"></script>
<script type="text/javascript" src="<c:url value="/resources/js/gmap3.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $('#spot-image').load(function(){
	    	$('#image-url-hid').val($(this).attr('src'));
			var top = ($('#spot-image').height()-$('#upload-image-btn').height())/2;
			var left = ($('#spot-image').width()-$('#upload-image-btn').width())/2;
			$('#upload-image-btn').css('top', top);
	    	$('#upload-image-btn').css('left', left);
    	});
	    $('#create-spot-form').ajaxForm({ 
	        dataType:  'json', 
	        beforeSubmit: function(formData, jqForm, options){
	        	formData.imageUrl = $('#spot-image').attr('src');
	        },
	        success:  function(data){
	        	if(!data || data.resultCode != 'SUCCESS' ) return;
	        	window.location.href = $('#return-btn').attr('href');
	        },
	        complete: function(jqXHR, textStatus){
          		
          	}
	    });
	});
	function getLocateObj(){
		return {fullAddr : $('#full-addr-input').val(), 
			lngLat: $('#full-addr-input').data('lngLat'),
			placeId: $('#place-id-hid').val()};
	}
	function locateCallback(place){
		$('#place-id-hid').val(place.id);
		$('#full-addr-input').val(place.fullAddr);
		$('#full-addr-input').data('lngLat', place.lngLat);
	}
</script>
</body>
</html>