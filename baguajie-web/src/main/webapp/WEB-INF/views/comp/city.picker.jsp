<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="${param.domId}" class="city-picker dropdown-menu comp-container">
	<h4 class="departures_title">从下列城市选择</h4>
	<h5>热门城市</h5>
	<a href="javascript:void(0);" title="quanguo">全国</a>
	<a href="javascript:void(0);" title="beijing">北京</a>
	<a href="javascript:void(0);" title="shanghai">上海</a>
	<a href="javascript:void(0);" title="guangzhou">广州</a>
	<a href="javascript:void(0);" title="shenzhen">深圳</a>
	<a href="javascript:void(0);" title="nanjing">南京</a>
	<a href="javascript:void(0);" title="hangzhou">杭州</a>
	<a href="javascript:void(0);" title="chengdu">成都</a>
	<a href="javascript:void(0);" title="xiamen">厦门</a>
	<a href="javascript:void(0);" title="wuhan">武汉</a>
	<a href="javascript:void(0);" title="qingdao">青岛</a>
	<a href="javascript:void(0);" title="tianjin">天津</a>
	<h5 class="dotline">省会城市</h5>
	<a href="javascript:void(0);" title="shenyang">沈阳</a>
	<a href="javascript:void(0);" title="changchun">长春</a>
	<a href="javascript:void(0);" title="changsha">长沙</a>
	<a href="javascript:void(0);" title="fuzhou">福州</a>
	<a href="javascript:void(0);" title="guiyang">贵阳</a>
	<a href="javascript:void(0);" title="hefei">合肥</a>
	<a href="javascript:void(0);" title="haikou">海口</a>
	<a href="javascript:void(0);" title="jinan">济南</a>
	<a href="javascript:void(0);" title="kunming">昆明</a>
	<a href="javascript:void(0);" title="lasa">拉萨</a>
	<a href="javascript:void(0);" title="lanzhou">兰州</a>
	<a href="javascript:void(0);" title="nanchang">南昌</a>
	<a href="javascript:void(0);" title="nanning">南宁</a>
	<a href="javascript:void(0);" title="taiyuan">太原</a>
	<a href="javascript:void(0);" title="xian">西安</a>
	<a href="javascript:void(0);" title="xining">西宁</a>
	<a href="javascript:void(0);" title="yinchuan">银川</a>
	<a href="javascript:void(0);" title="zhengzhou">郑州</a>
	<br clear="all">
	<a href="javascript:void(0);" title="haerbin">哈尔滨</a>
	<a href="javascript:void(0);" title="shijiazhuang">石家庄</a>
	<a class="widthfix" href="javascript:void(0);" title="huhehaote">呼和浩特</a>
	<a class="widthfix" href="javascript:void(0);" title="wulumuqi">乌鲁木齐</a>
	<h5 class="dotline">其它城市</h5>
	<a href="javascript:void(0);" title="baotou">包头</a>
	<a href="javascript:void(0);" title="chongqing">重庆</a>
	<a href="javascript:void(0);" title="changzhou">常州</a>
	<a href="javascript:void(0);" title="dalian">大连</a>
	<a href="javascript:void(0);" title="dongguan">东莞</a>
	<a href="javascript:void(0);" title="foshan">佛山</a>
	<a href="javascript:void(0);" title="jiangmen">江门</a>
	<a href="javascript:void(0);" title="kashi">喀什</a>
	<a href="javascript:void(0);" title="mianyang">绵阳</a>
	<a href="javascript:void(0);" title="ningbo">宁波</a>
	<a href="javascript:void(0);" title="quanzhou">泉州</a>
	<a href="javascript:void(0);" title="sanya">三亚</a>
	<a href="javascript:void(0);" title="shantou">汕头</a>
	<a href="javascript:void(0);" title="suzhou">苏州</a>
	<a href="javascript:void(0);" title="taizhou">台州</a>
	<a href="javascript:void(0);" title="weihai">威海</a>
	<a href="javascript:void(0);" title="wuxi">无锡</a>
	<a href="javascript:void(0);" title="wenzhou">温州</a>
	<a href="javascript:void(0);" title="xichang">西昌</a>
	<a href="javascript:void(0);" title="xuzhou">徐州</a>
	<a href="javascript:void(0);" title="yuncheng">运城</a>
	<a href="javascript:void(0);" title="yanji">延吉</a>
	<a href="javascript:void(0);" title="yulin">榆林</a>
	<a href="javascript:void(0);" title="yantai">烟台</a>
	<a href="javascript:void(0);" title="yiwu">义乌</a>
	<a href="javascript:void(0);" title="zhongshan">中山</a>
	<a href="javascript:void(0);" title="zhuhai">珠海</a>
	<a href="javascript:void(0);" title="hailaer">海拉尔</a>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('#${param.domId} a').click(function(){
			var pinyin = $(this).attr('title').toLowerCase();
			var label = $(this).text();
		 /* var w_href = window.location.href;
			var base_url = '<c:url value="/" />', url;
			var map_idx = w_href.indexOf('/map');
			if(map_idx==-1){
				url = base_url;
			}else{
				url = w_href.substring(0, map_idx +4);
			}
			if(url.charAt(url.length-1) != '/'){
				url += '/';
			}
			url += pinyin;
			window.location.href = url; */
			$(this).parent().trigger('city-click',[pinyin, label]);
		});
		$('#${param.domId}').bind('city-open', function(e, pinyin){
			$(this).find('a.active').removeClass('active');
			$(this).find('a[title='+pinyin+']').addClass('active');
		});
	});
</script>