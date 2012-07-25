<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="subnav board row-fluid content-wrapper" style="width: 1150px">
	<div class="span10 glk clear">
		<div class="btn-group f-l" data-toggle="buttons-radio">
			<button class="btn active">所有八卦</button>
			<button class="btn">人气八卦</button>
			<c:forEach var="cat" begin="0" end="3" 
				items="${signInUserPrefer.categories}">
				<button class="btn">${cat}</button>
			</c:forEach>
		</div>
		<c:if test="${signInUserPrefer.categories.size()>4}">
			<div class="btn-group f-l ml-2">
				<a class="btn dropdown-toggle" href="#" data-toggle="dropdown">
					更多分类
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<c:forEach var="cat" begin="4" 
						items="${signInUserPrefer.categories}">
						<li><a href="#">${cat}</a></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
		
		<div class="edit f-l">
			<a href="#" title="管理我的分类"><i class="icon-pencil"></i></a>
		</div>
	</div>
	<div class="span2 view-t">
		<div class="btn-group f-r" data-toggle="buttons-radio">
			<c:set var="wf" value="wf"/>
			<c:set var="mv" value="mv"/>
			<button class="btn wf <c:if test="${param.viewType eq wf}">active</c:if>" data-href="<c:url value="/" />" ><i class="icon-th"></i></button>
			<button class="btn mv <c:if test="${param.viewType eq mv}">active</c:if>" data-href="<c:url value="/map" />"><i class="icon-map-marker"></i></button>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('.view-t button').click(function(){
			var href = $(this).attr('data-href');
			window.location.href= href;
		});
	});
</script>