<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="back-top" class="dis-n">
	<a href="javascript:void(0)" class="btn btn-info"><i class="icon-arrow-up icon-white"></i><br>返<br>回<br>顶<br>部</a>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#back-top a").click(function() {
			$("html,body").animate({
				"scrollTop" : "0"
			}, "slow");
		})
		$(window).scroll(function() {
			if ($(window).scrollTop() > 100) {
				$("#back-top").fadeIn(200);
			} else {
				$("#back-top").fadeOut(200);
			}
		})
	})
</script>
