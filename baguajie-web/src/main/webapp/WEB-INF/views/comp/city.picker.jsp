<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="${param.domId}" class="city-picker dropdown-menu comp-container">
	<h4 class="departures_title">从下列城市选择</h4>
	<h5>热门城市</h5>
	<a href="javascript:void(0);" title="Quanguo">全国</a>
	<a href="javascript:void(0);" title="Beijing">北京</a>
	<a href="javascript:void(0);" title="Shanghai">上海</a>
	<a href="javascript:void(0);" title="Guangzhou">广州</a>
	<a href="javascript:void(0);" title="Shenzhen">深圳</a>
	<a href="javascript:void(0);" title="Nanjing">南京</a>
	<a href="javascript:void(0);" title="Hangzhou">杭州</a>
	<a href="javascript:void(0);" title="Chengdu">成都</a>
	<a href="javascript:void(0);" title="Xiamen">厦门</a>
	<a href="javascript:void(0);" title="Wuhan">武汉</a>
	<a href="javascript:void(0);" title="Qingdao">青岛</a>
	<a href="javascript:void(0);" title="Tianjin">天津</a>
	<h5 class="dotline">省会城市</h5>
	<a href="javascript:void(0);" title="Shenyang">沈阳</a>
	<a href="javascript:void(0);" title="Changchun">长春</a>
	<a href="javascript:void(0);" title="Changsha">长沙</a>
	<a href="javascript:void(0);" title="Fuzhou">福州</a>
	<a href="javascript:void(0);" title="Guiyang">贵阳</a>
	<a href="javascript:void(0);" title="Hefei">合肥</a>
	<a href="javascript:void(0);" title="Haikou">海口</a>
	<a href="javascript:void(0);" title="Jinan">济南</a>
	<a href="javascript:void(0);" title="Kunming">昆明</a>
	<a href="javascript:void(0);" title="Lasa">拉萨</a>
	<a href="javascript:void(0);" title="Lanzhou">兰州</a>
	<a href="javascript:void(0);" title="Nanchang">南昌</a>
	<a href="javascript:void(0);" title="Nanning">南宁</a>
	<a href="javascript:void(0);" title="Taiyuan">太原</a>
	<a href="javascript:void(0);" title="Xi'an">西安</a>
	<a href="javascript:void(0);" title="Xining">西宁</a>
	<a href="javascript:void(0);" title="Yinchuan">银川</a>
	<a href="javascript:void(0);" title="Zhengzhou">郑州</a>
	<br clear="all">
	<a href="javascript:void(0);" title="Haerbin">哈尔滨</a>
	<a href="javascript:void(0);" title="Shijiazhuang">石家庄</a>
	<a class="widthfix" href="javascript:void(0);" title="Huhehaote">呼和浩特</a>
	<a class="widthfix" href="javascript:void(0);" title="Wulumuqi">乌鲁木齐</a>
	<h5 class="dotline">其它城市</h5>
	<a href="javascript:void(0);" title="Baotou">包头</a>
	<a href="javascript:void(0);" title="Chongqing">重庆</a>
	<a href="javascript:void(0);" title="Changzhou">常州</a>
	<a href="javascript:void(0);" title="Dalian">大连</a>
	<a href="javascript:void(0);" title="Dongguan">东莞</a>
	<a href="javascript:void(0);" title="Foshan">佛山</a>
	<a href="javascript:void(0);" title="Jiangmen">江门</a>
	<a href="javascript:void(0);" title="Kashi">喀什</a>
	<a href="javascript:void(0);" title="Mianyang">绵阳</a>
	<a href="javascript:void(0);" title="Ningbo">宁波</a>
	<a href="javascript:void(0);" title="Quanzhou">泉州</a>
	<a href="javascript:void(0);" title="Sanya">三亚</a>
	<a href="javascript:void(0);" title="Shantou">汕头</a>
	<a href="javascript:void(0);" title="Suzhou">苏州</a>
	<a href="javascript:void(0);" title="Taizhou">台州</a>
	<a href="javascript:void(0);" title="Weihai">威海</a>
	<a href="javascript:void(0);" title="Wuxi">无锡</a>
	<a href="javascript:void(0);" title="Wenzhou">温州</a>
	<a href="javascript:void(0);" title="Xichang">西昌</a>
	<a href="javascript:void(0);" title="Xuzhou">徐州</a>
	<a href="javascript:void(0);" title="Yuncheng">运城</a>
	<a href="javascript:void(0);" title="Yanji">延吉</a>
	<a href="javascript:void(0);" title="Yulin">榆林</a>
	<a href="javascript:void(0);" title="Yantai">烟台</a>
	<a href="javascript:void(0);" title="Yiwu">义乌</a>
	<a href="javascript:void(0);" title="Zhongshan">中山</a>
	<a href="javascript:void(0);" title="Zhuhai">珠海</a>
	<a href="javascript:void(0);" title="Hailaer">海拉尔</a>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('city-picker a').click(function(){
			var pinyin = $(this).attr('title').toLowerCase();
			var w_href = window.location.href;
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
			window.location.href = url;
		});
	});
</script>