<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="upload-image-modal" class="modal hide" 
		style="width: 450px;">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h3>上传、引用图片</h3>
	</div>
	<div class="modal-body upload">
		<div class="tabbable">
    		<ul class="nav nav-pills">
    			<li class="active"><a href="#from-local" data-toggle="pill">本地上传</a></li>
 			    <li><a href="#from-site-address" data-toggle="pill">引用网址</a></li>
    		</ul>
    		<div class="tab-content">
    			<div class="tab-pane active" id="from-local">
    				<div class="row-fluid">
    					<div class="span10">
    						<div class="well">
    							<div id="image-file-queue"></div>
    						</div>
    					</div>
						<div class="span2 ta-r">
							<div id="image-file-input-wrapper">
								<input class="none" type="file" id="image-file-input" />
							</div>
						</div>
    				</div>
    			</div>
    			<div class="tab-pane" id="from-site-address">
    				<div class="row-fluid">
    					<div class="span10">
    						<input class="span9" id="net-address" name="netAdress" type="text" 
									placeholder="输入图片网址" autocomplete="off" />
    					</div>
						<div class="span2 ta-r">
							<a id="refer-from-site-btn" class="btn upload btn-primary" href="javascript:void(0)">引用</a>
						</div>
    				</div>
    			</div>
    		</div>
    	</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('#image-file-input').uploadify({
		    uploader  	: '<c:url value="/resources/js/uploadify.swf" />',
		    script   	: '<c:url value="/spots/upload" />',
		    scriptData  : { 'session': '${pageContext.session.id}'},
		    cancelImg 	: '<c:url value="/resources/img/cancel.png" />',
		    folder 		: '<c:url value="/temp" />',
		    fileDataName : 'imageFile',
		    buttonText	: '浏览...',
		    fileDesc  	: "Image File",
		    fileExt		: "*.jpg;*.png;*.jpeg;*.gif;*.bmp",
		    sizeLimit 	: 8000000,
		    queueID		: 'image-file-queue',
		    multi     	: false,
		    simUploadLimit : 1,
		    auto		: true,
		    removeCompleted: true,
		    hideButton  : true,
		    wmode     	: 'transparent',
		    onSelectOnce : function(event,data) {
		      
		    },
		    onComplete 	: function(event, ID, fileObj, response, data) {
		    	$('#image-file-input').uploadifyClearQueue();
		    	$('#upload-image-modal').modal('hide');
		    	$('#spot-image').attr('src', response);
		    },
		    onError		: function (event, queueID ,fileObj, errorObj) {
              	
            }
		});
		$('#image-file-input-wrapper').mouseenter(function(){
			$('#image-file-inputUploader').css('background-position', '-60px 0px');
		});
		$('#image-file-input-wrapper').mouseleave(function(){
			$('#image-file-inputUploader').css('background-position', '0px 0px');
		});
		/*
	    $('#upload-image-modal').on('show', function () {
	        // do something…
	    });
	    $('#upload-image-modal').on('hide', function () {
	    	$('#image-file-input').uploadifyClearQueue();
	    });
	    */
		$('#refer-from-site-btn').click(function(){
			var addr = $('#net-address').val();
			if($.trim(addr)){
				$('#spot-image').attr('src', addr);
				$('#upload-image-modal').modal('hide');
				$('#net-address').val('');
			}
		});
	});
</script>