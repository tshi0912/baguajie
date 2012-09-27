<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="sign-in-modal" class="modal hide fade" 
		style="width: 450px;">
	<form id="signInForm" class="mb-0 fs-14 lh-16 sign-in" action="<c:url value="/signin" />" method="post">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h3>登录八卦街</h3>
	</div>
	<div class="modal-body">
		<div class="row-fluid mt-10">
			<input id="signInName" class="validate[required,custom[email]] input-large" name="signInName" type="text"
				data-prompt-position="centerRight:0,-4" placeholder="八卦街注册邮箱" autocomplete="off" /></div>
		<div class="row-fluid mt-10">
			<input id="signInPassword" class="validate[required] input-large" name="signInPassword" type="password"
				data-prompt-position="centerRight:0,-4" placeholder="输入密码" autocomplete="off" /></div>
		<div class="row-fluid mt-10">
			<a href="<c:url value="/signup" />">还没有八卦街账号？立即注册！</a>
			<a href="#" class="f-r">忘记密码？</a>
		</div>
	</div>
	<div class="modal-footer">
		<input type="submit" class="btn btn-primary btn-large" value="登录"></input>
	</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		$('#signInForm').validationEngine({
			prettySelect: true,
			useSuffix: '_target',
			promptPosition: 'centerRight',
			autoPositionUpdate: true,
			ajaxFormValidation: true,
			ajaxFormValidationMethod: 'post',
			ajaxFormValidationURL: '<c:url value="/signin/validate" />',
			onBeforeAjaxFormValidation: function(form, options){
				form.find('.modal-footer input[type="submit"]').button('loading');
			},
			onAjaxFormComplete: function(status, form, errors, options){
				form.find('.modal-footer input[type="submit"]').button('reset')
				if(status == true){
					$('#sign-in-modal').modal('hide');
					$.get('<c:url value="/signin/navbar" />', function(data){
						var statefulNav = $.trim(data);
						if(statefulNav){
							$('.navbar .nav.pull-right').replaceWith(statefulNav);
						}
					})
				}
			}
		});
	});
</script>