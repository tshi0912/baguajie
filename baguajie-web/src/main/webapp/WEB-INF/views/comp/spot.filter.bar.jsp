<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="spot-filter-bar" class="filter-bar subnav board row-fluid content-wrapper" style="width: 1150px">
	<div class="span10 row-fluid">
		<div class="p-5 f-l fs-16">
			<span>八卦搜索：</span>
		</div>
		<form id="spot-search" class="navbar-search mt-0" action="">
		<c:forEach var="filter" items="${filters}">
			<c:choose>
			<c:when test="${filter.type eq 'city'}">
			<div class="btn-group ${filter.type} bin fs-14 mr-10 f-l">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<span class="c-666">${filter.typeLabel}:</span>
					<strong>${filter.label}</strong>
					<input type="hidden" name="<c:out value="${filter.type}"/>" value="${filter.value}"></input>
					<b class="caret"></b>
				</a>
				<jsp:include page="/WEB-INF/views/comp/city.picker.jsp">
					<jsp:param name="domId" value="spot-filter-city-picker"/>
				</jsp:include>
			</div>
			</c:when>
			<c:when test="${filter.type eq 'category'}">
			<div class="btn-group ${filter.type} bin fs-14 mr-10 f-l">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<span class="c-666">${filter.typeLabel}:</span>
					<strong>${filter.label}</strong>
					<input type="hidden" name="<c:out value="${filter.type}"/>" value="${filter.value}"></input>
					<b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a href="#" data-value="">全部</a></li>
					<li><a href="#" data-value="新闻">新闻</a></li>
					<li><a href="#" data-value="美食">美食</a></li>
					<li><a href="#" data-value="美图">美图</a></li>
					<li><a href="#" data-value="娱乐">娱乐</a></li>
					<li><a href="#" data-value="购物">购物</a></li>
				</ul>
			</div>
			</c:when>
			<c:otherwise>
			<div class="search-box fs-14 f-l">
				<span>${filter.typeLabel}:</span>
				<input class="search-query search-input span3" name="${filter.type}" 
					type="text" placeholder="请输入想要搜索的关键词..." value="${filter.value}">
				<i class="icon-search p-a" style="right: 5px; top: 7px;"></i>
			</div>
			</c:otherwise>
			</c:choose>
		</c:forEach>
		</form>
	</div>
	<div class="span2 view-t">
		<div class="btn-group f-r" data-toggle="buttons-radio">
			<button class="btn wf <c:if test="${param.viewType eq 'wf'}">active</c:if>" data-href="<c:url value="/" />" data-original-title="瀑布布局" rel="tooltip" ><i class="icon-th"></i></button>
			<button class="btn mv <c:if test="${param.viewType eq 'mv'}">active</c:if>" data-href="<c:url value="/map" />" data-original-title="地图布局" rel="tooltip"><i class="icon-map-marker"></i></button>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#spot-filter-city-picker a').click(function(){
			var pinyin = $(this).attr('title').toLowerCase();
			var p_container = $(this).parents('.btn-group.city');
			var h_input = p_container.find('input');
			var s_label = p_container.find('a strong');
			h_input.val(pinyin);
			s_label.text($(this).text());
			$('#spot-search').submit();
		});
		
		$('.btn-group.category li a').click(function(){
			var data_value = $(this).attr('data-value');
			var p_container = $(this).parents('.btn-group.category');
			var h_input = p_container.find('input');
			var s_label = p_container.find('a strong');
			h_input.val(data_value);
			s_label.text($(this).text());
			$('#spot-search').submit();
		});
		
		$('#spot-search').on('submit', function(){
			var params = {};
			$(this).find('input').each(function(){
				params[$(this).attr('name')] = $(this).val();
			});
			setTimeout(function(){
				op.update_query_str(params);
			}, 500);
			return false;
		});
		$('.view-t button').click(function(){
			var href = $(this).attr('data-href');
			window.location.href= href;
		});
		$('[rel="tooltip"]').tooltip();
	});
</script>