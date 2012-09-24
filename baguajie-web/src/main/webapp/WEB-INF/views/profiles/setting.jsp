<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://baguajie.net/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>${signInUser.name} 设置账号 八卦街 有图有真相</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-responsive.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-base.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-theme.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/js/ga.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.1.7.1.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/baguajie.init.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/baguajie.op.js" />" ></script>
</head>
<body class="front">
	<jsp:include page="/WEB-INF/views/comp/header.jsp">
		<jsp:param name="noNeed" value="true"/>
		<jsp:param name="tab" value="none"/>
	</jsp:include>
	<div class="container mb-30 board">
		<div class="pl-30 pr-30 pt-20 bg-gray">
		    <ul class="nav nav-tabs fs-14" 
		    	style="margin-left: -30px; margin-right: -30px;  margin-bottom:0px">
		    	<li class="active"  style="margin-left:30px">
		   			<a data-toggle="tab" href="#basic-info">基本信息</a></li>
		   		<li class="ml-10">
	   				<a data-toggle="tab" href="#change-avatar">修改头像</a></li>
	   			<li class="ml-10">
	   				<a data-toggle="tab" href="#change-pwd">修改密码</a></li>		
	   			<li class="">
	   				<a data-toggle="tab" href="#binding-weibo">关联三方账号</a></li>				    
			</ul>
	    </div>
	    <div class="tab-content p-20 of-v">
		    <div id="basic-info" class="tab-pane active fade in">
		    	<c:url value="/setting/basic" var="ub_url"/> 
		    	<form:form id="basicInfoForm" action="${ub_url}" method="post" class="form-horizontal mt-20">
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputName">请叫我</label>
		    			<div class="controls fs-16 lh-18">
							<input id="inputName" class="input-xlarge" type="text" name="name" 
								value="${signInUser.name}" style="height: 25px;">
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-30 c-888" for="inputGender">我是</label>
		    			<div class="controls fs-16 lh-18">
		    				<input id="inputGender" type="hidden" name="gender" value="${signInUser.gender}">
		    				<div class="btn-group" data-toggle="buttons-radio" data-toggle-name="gender">
		    					<button id="inputMale" class="btn btn-large btn-info fs-16" type="button" data-val="FEMALE">美女</button>
		    					<button id="inputFemale" class="btn btn-large btn-info fs-16" type="button" data-val="MALE">帅哥</button>
		    				</div>
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-30 c-888" for="inputCity">居住在</label>
		    			<div class="controls fs-16 lh-18">
		    				<input id="inputCity" type="hidden" name="city" placeholder="" value="${signInUser.city}">
		    				<div class="btn-group">
								<a id="city-btn" class="btn btn-large dropdown-toggle fs-16" data-toggle="dropdown" href="#">
									<strong>未知</strong>
									<b class="caret"></b>
								</a>
								<jsp:include page="/WEB-INF/views/comp/city.picker.jsp">
									<jsp:param name="domId" value="setting-city-picker"/>
								</jsp:include>
							</div>
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-20 c-888" for="inputSummary">我想说</label>
		    			<div class="controls fs-16 lh-18">
							<textarea id="inputSummary" class="input-xlarge" type="text" name="summary" rows="3" >${signInUser.summary}</textarea>
						</div>
		    		</div>
		    		<div class="form-actions">
		    			<div class="dis-i">
						    <button type="submit" class="btn btn-large btn-primary" data-loading-text="更新中...">保存修改</button>
					    	<i class="icon-ok ml-10 dis-n"></i>
				    	</div>
				    </div>
		    	</form:form>
		    </div>
		    <div id="change-avatar" class="tab-pane fade">
		    	<c:url value="/setting/avatar" var="ua_url"/> 
		    	<form:form id="avatarForm" action="${ua_url}" method="post" class="form-horizontal mt-20">
		    		<div class="form-actions">
		    			<div class="dis-i">
						    <button type="submit" class="btn btn-large btn-primary" data-loading-text="更新中...">保存修改</button>
					    	<i class="icon-ok ml-10 dis-n"></i>
				    	</div>
				    </div>
		    	</form:form>
		    </div>
		    <div id="change-pwd" class="tab-pane fade">
		    	<c:url value="/setting/changepwd" var="cp_url"/> 
		    	<form:form id="changePwdForm" action="${cp_url}" method="post" class="form-horizontal mt-20">
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputOldPwd">旧密码</label>
		    			<div class="controls fs-16 lh-18">
							<input id="inputOldPwd" class="input-xlarge" type="password" name="oldPwd" 
								value="" style="height: 25px;">
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputNewPwd">新密码</label>
		    			<div class="controls fs-16 lh-18">
							<input id="inputNewPwd" class="input-xlarge" type="password" name="newPwd" 
								value="" style="height: 25px;">
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputNewPwdRe">确认新密码</label>
		    			<div class="controls fs-16 lh-18">
							<input id="inputNewPwdRe" class="input-xlarge" type="password" name="newPwdRe" 
								value="" style="height: 25px;">
						</div>
		    		</div>
		    		<div class="form-actions">
		    			<div class="dis-i">
						    <button type="submit" class="btn btn-large btn-primary" data-loading-text="更新中...">保存修改</button>
					    	<i class="icon-ok ml-10 dis-n"></i>
				    	</div>
				    </div>
		    	</form:form>
		    </div>
		    <div id="binding-weibo" class="tab-pane fade">
		    	<c:url value="/setting/binding" var="bw_url"/> 
		    	<form:form id="bindingForm" action="${bw_url}" method="post" class="form-horizontal mt-20">
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputName">新浪微博</label>
		    			<div class="controls fs-16 lh-18">
							<button class="btn btn-large btn-danger" data-toggle="button" type="button">绑定新浪微博</button>
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputName">人人网</label>
		    			<div class="controls fs-16 lh-18">
							<button class="btn btn-large btn-info" data-toggle="button" type="button">绑定人人网</button>
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputName">豆瓣</label>
		    			<div class="controls fs-16 lh-18">
							<button class="btn btn-large btn-success" data-toggle="button" type="button">绑定豆瓣</button>
						</div>
		    		</div>
		    		<div class="control-group" style="margin-bottom: 30px;">
		    			<label class="control-label fs-18 lh-25 c-888" for="inputName">腾讯微博</label>
		    			<div class="controls fs-16 lh-18">
							<button class="btn btn-large btn-primary" data-toggle="button" type="button">绑定腾讯微博</button>
						</div>
		    		</div>
		    	</form:form>
		    </div>
		</div>
	</div>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui-1.8.18.custom.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.scrollTo.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.form.js" />"></script>
<script type="text/javascript">
	$(function(){
		$('#basicInfoForm').ajaxForm({ 
	        dataType:  'json', 
	        beforeSubmit: function(formData, jqForm, options){
	        	$('#basicInfoForm .form-actions button').button('loading');
	        	$('#basicInfoForm .icon-ok').hide();
	        },
	        success:  function(data){
	        	if(!data || data.resultCode != 'SUCCESS' ) return;
	        	$('#basicInfoForm .icon-ok').css('display', 'inline-block');
	        },
	        complete: function(jqXHR, textStatus){
          		$('#basicInfoForm .form-actions button').button('reset')
          	}
	    });
		$('#changePwdForm').ajaxForm({ 
	        dataType:  'json', 
	        beforeSubmit: function(formData, jqForm, options){
	        	$('#changePwdForm .form-actions button').button('loading');
	        	$('#changePwdForm .icon-ok').hide();
	        },
	        success:  function(data){
	        	if(!data || data.resultCode != 'SUCCESS' ) return;
	        	$('#changePwdForm .icon-ok').css('display', 'inline-block');
	        },
	        complete: function(jqXHR, textStatus){
          		$('#changePwdForm .form-actions button').button('reset')
          	}
	    });
		$('div.btn-group[data-toggle-name=*]').each(function(){
		    var group   = $(this);
		    var form    = group.parents('form').eq(0);
		    var name    = group.attr('data-toggle-name');
		    var hidden  = $('input[name="' + name + '"]', form);
		    $('button', group).each(function(){
		    	var button = $(this);
		      	button.live('click', function(){
		        	hidden.val($(this).attr('data-val'));
		      	});
		      	if(button.attr('data-val') == hidden.val()) {
		     		button.addClass('active');
		    	}
		    });
		});
		var city = $('#inputCity').val();
		if($.trim(city)){
			var targetCityElem = $('#setting-city-picker a[title='+city+']').addClass('active');
			$('#city-btn strong').text(targetCityElem.text());
		}else{
			$('#city-btn strong').text('-请选择-');
		}
		$('#setting-city-picker').bind('city-click', function(e, pinyin, label){
			$('#inputCity').val(pinyin);
			$('#inputCity').parent().find('.btn-group strong').text(label);
		});
		$('#setting-city-picker').parent().bind('dropdown-open', function(e){
			var pinyin = $('#inputCity').val();
			$('#setting-city-picker').trigger('city-open', [pinyin]);
		});
	});
</script>
</body>
</html>