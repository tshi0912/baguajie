<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>注册 八卦街 有图有真相</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-base.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/baguajie-theme.css" />" />
</head>
<body>
	<div class="container">
		<div class="row mt-30" >
			<div class="span8 offset2 mt-20">
				<div class="hero-unit board">
					<h1>八卦街</h1>
					<p>完成注册，成为八哥八姐！</p>
					<form:form cssClass="sign-in mt-30" modelAttribute="signUpUserVo" method="post">
						<div class="row-fluid ">
							<div class="f-l"><input id="email" class="input-xlarge" name="email" type="text"
									placeholder="输入八卦街注册邮箱" autocomplete="off" value="<c:out value="${signUpUserVo.email}" />" /></div>
							<div class="f-l hint"><form:errors path="email" cssClass="alert alert-error" /></div>
						</div>
						<div class="row-fluid mt-10">
							<div class="f-l"><input id="name" class="input-xlarge" name="name" type="text"
									placeholder="给自己取个昵称" autocomplete="off" value="<c:out value="${signUpUserVo.name}" />" /></div>
							<div class="f-l hint"><form:errors path="name" cssClass="alert alert-error"/></div>
						</div>
						<div class="row-fluid mt-10">
							<div class="f-l"><input id="password" class="input-xlarge" name="password" type="password"
									placeholder="输入密码" autocomplete="off" value="<c:out value="${signUpUserVo.password}" />" /></div>
							<div class="f-l hint"><form:errors path="password" cssClass="alert alert-error"/></div>
						</div>
						<div class="row-fluid mt-10">
							<div class="f-l"><input id="password-re" class="input-xlarge" name="passwordRe" type="password"
									placeholder="再次输入密码" autocomplete="off" value="<c:out value="${signUpUserVo.passwordRe}" />" /></div>
							<div class="f-l hint"><form:errors path="" cssClass="alert alert-error"/></div>
						</div>
						<div class="row-fluid mt-10">
							<div class="span6">
							 	<button type="submit" class="btn btn-primary btn-large">注册</button>
							 	<a class="btn btn-large ml-10" href="<c:url value="/" />">返回</a>
							 </div>
							<div class="span6 ta-r"><a href="<c:url value="/signin" />">我有账号了，立即去登录！</a></div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>