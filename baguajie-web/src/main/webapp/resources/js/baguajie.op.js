var op = {
	/* variables */
	_signin_ts: null,
	_signin_timeout: 30*60*1000,
		
	/* functions */
	calc_utctime: function(date){
		if(!date) return NaN;
		//getTimezoneOffset returns the time zone offset in minutes 
		return date.getTime() + 
			(date.getTimezoneOffset() * 60000);
	},
	
	get_signin_ts: function(){
		var ts = $.trim($('#header-hiddens input[name="signin-ts"]').val());
		if(ts){
			op._signin_ts = parseFloat(ts+'');
		}
	},
	
	update_signin_ts: function(ts){
		$('#header-hiddens input[name="signin-ts"]').val(ts);
	},
	
	check_signin: function(callback, unsigninhandler){
		if(!this._signin_ts || this._signin_ts == -1){
			unsigninhandler? unsigninhandler() : this.redirect_to_signin();
		}
		if(this.calc_utctime(new Date()) - this._signin_ts 
				< this._signin_timeout){
			callback? callback(): null;
		}else{
			$.getJSON(web_context+'/checksignin', function(data){
				if(data && data.resultCode == 'SUCCESS'){
					this.update_signin_ts(data.resultData);
					callback? callback(): null;
				}else{
					unsigninhandler? unsigninhandler(): this.redirect_to_signin();
				}
			});
		}
	},
	
	redirect_to_signin: function(){
		window.location.href= web_context + "/signin";
	},
	
	follow: function(dom){
		var url= $(dom).attr('href');
		$.getJSON(url, function(data){
			if(data && data.resultCode == 'SUCCESS'){
				$(dom).removeClass('follow').removeClass('btn-success')
					.addClass('defollow')
					.attr('href', url.replace('/follow/', '/defollow/'))
					.find('span').text('取消关注');
				$(dom).find('i').removeClass('icon-white');
				var idr_str = $(dom).attr('data-idr');
				if($.trim(idr_str)){
					$(idr_str).text(parseFloat($(idr_str).text())+1+'');
				}
				op.notify_header('已关注');
			}
		});
	},
	
	defollow: function(dom){
		var url= $(dom).attr('href');
		$.getJSON(url, function(data){
			if(data && data.resultCode == 'SUCCESS'){
				$(dom).removeClass('defollow')
					.addClass('follow').addClass('btn-success')
					.attr('href', url.replace('/defollow/', '/follow/'))
					.find('span').text('关注Ta');
				$(dom).find('i').addClass('icon-white');
				var idr_str = $(dom).attr('data-idr');
				if($.trim(idr_str)){
					$(idr_str).text(parseFloat($(idr_str).text())-1+'');
				}
				op.notify_header('已取消关注');
			}
		});
	},
	
	change_follow: function(dom, idr){
		if($(dom).hasClass('follow')){
			op.follow(dom, idr);
		}else if($(dom).hasClass('defollow')){
			op.defollow(dom, idr);
		}
	},
	
	track: function(dom){
		this.check_signin( function(){
			var url= $(dom).attr('href');
			$.getJSON(url, function(data){
				if(data && data.resultCode == 'SUCCESS'){
					$(dom).removeClass('track').removeClass('btn-success')
						.addClass('detrack')
						.attr('href', url.replace('/track/', '/detrack/'))
						.find('span').text('取消追踪');
					$(dom).find('i').removeClass('icon-white');
					var idr_str = $(dom).attr('data-idr');
					if($.trim(idr_str)){
						$(idr_str).text(parseFloat($(idr_str).text())+1+'');
					}
					op.notify_header('已追踪');
				}
			});
		}, null);
	},
	
	detrack: function(dom){
		var url= $(dom).attr('href');
		$.getJSON(url, function(data){
			if(data && data.resultCode == 'SUCCESS'){
				$(dom).removeClass('detrack')
					.addClass('track').addClass('btn-success')
					.attr('href', url.replace('/detrack/', '/track/'))
					.find('span').text('追踪');
				$(dom).find('i').addClass('icon-white');
				var idr_str = $(dom).attr('data-idr');
				if($.trim(idr_str)){
					$(idr_str).text(parseFloat($(idr_str).text())-1+'');
				}
				op.notify_header('已取消追踪');
			}
		});
	},
	
	show_forward: function(dom){
		this.check_signin( function(){
			var pin = $(dom).parents('.pin');
			var form = pin.find('.write').show().find('form.act-cmt');
			form.attr('action', form.attr('data-fwd-url')).addClass('fwd');
			var cmt_ta = form.find('textarea').focus().val('转发八卦: ');
			$(pin).parent().masonry('reload');
			$.scrollTo(cmt_ta, 800, {
				offset: {
					top: -300
				},
				margin: true
			  }
			);
		}, null);
	},
	
	show_comment: function(dom){
		this.check_signin( function(){
			var pin = $(dom).parents('.pin');
			var form = pin.find('.write').show().find('form.act-cmt');
			form.attr('action', form.attr('data-cmt-url'));
			var cmt_ta = form.find('textarea').focus().val('');
			$(pin).parent().masonry('reload');
			$.scrollTo(cmt_ta, 800, {
				offset: {
					top: -300
				},
				margin: true
			  }
			);
		}, null);
	},
	
	toggle_cmt_dashboard: function(dom){
		var act = $(dom).parents('.act-list');
		act.find('.fwds-box').hide();
		var cmts_box = act.find('.cmts-box');
		if(cmts_box.css('display') == 'none'){
			cmts_box.show();
			var first_load = cmts_box.data('first_load');
			if(!first_load){
				var actId = cmts_box.find('input[name="actId"]').val();
				op.get_cmts_dashboard(actId, 0, cmts_box.find('.cmts'));
			}
		}else{
			cmts_box.hide();
		}
	},
	
	toggle_fwd_dashboard: function(dom){
		var act = $(dom).parents('.act-list');
		act.find('.cmts-box').hide();
		var fwds_box = act.find('.fwds-box');
		if(fwds_box.css('display') == 'none'){
			fwds_box.show();
			var first_load = fwds_box.data('first_load');
			if(!first_load){
				var actId = fwds_box.find('input[name="actId"]').val();
				op.get_fwds_dashboard(actId, 0, fwds_box.find('.cmts'));
			}
		}else{
			fwds_box.hide();
		}
	},
	
	toggle_rep_dashboard: function(dom){
		var act = $(dom).parents('.act-list');
		act.find('.reps-box').hide();
		var reps_box = act.find('.reps-box');
		if(reps_box.css('display') == 'none'){
			reps_box.show();
			var first_load = reps_box.data('first_load');
			if(!first_load){
				var actId = reps_box.find('input[name="actId"]').val();
				op.get_reps_dashboard(actId, 0, reps_box.find('.cmts'));
			}
		}else{
			reps_box.hide();
		}
	},
	
	change_track: function(dom){
		if($(dom).hasClass('track')){
			op.track(dom);
		}else if($(dom).hasClass('detrack')){
			op.detrack(dom);
		}
	},
	
	pin_bind_event: function($dom){
		$dom.find('.op-view').mouseenter(function(){
			$(this).find('.op-list').fadeIn();
		});
		$dom.find('.op-view').mouseleave(function(){
			$(this).find('.op-list').fadeOut();
		});
		$dom.find('textarea').focus(function(){
			$(this).effect('highlight', {}, 1600);
		});
		$dom.find('[data-type="placemap"]').popoverx({
			width: 250,
			height: 200,
			auto_close: 10000,
			onShown: function(){
				var pinyin = this.$element.attr('data-city');
				var lngLat = this.$element.attr('data-lngLat');
				var idx = lngLat.indexOf(',');
				var lng = parseFloat(lngLat.substring(0, idx));
				var lat = parseFloat(lngLat.substring(idx+1, lngLat.length));
				var no_marker = false;
				if(isNaN(lng) || isNaN(lat)){
					no_marker = true;
				}
				var $this = this;
				var holder = this.$tip.find('.popover-content');
				if(!this.$element.data('cityMeta')){
					op.get_city_meta(pinyin, function(cityMeta){
						$this.$element.data('cityMeta', cityMeta);
						op.show_place_map(holder, no_marker, lng, lat, cityMeta);
					});
				}else{
					op.show_place_map(holder, no_marker, lng, lat, this.$element.data('cityMeta'));
				}
			}
		});
		$dom.find('[data-type="namecard"]').popoverx({
			fire_on : 'hover',
			auto_close: 10000,
			hover_delay_close: 1000,
			height: 150,
			onShown: function(){
				var $this = this;
				var uid = this.$element.attr('data-id');
				if(!this.$element.data('namecard')){
					op.show_name_card(uid, function(html){
						$this.$element.data('namecard', html);
						$this.$tip.find('.popover-content').html(html);
					});
				}else{
					this.$tip.find('.popover-content').html(this.$element.data('namecard'));
				}
			}
		});
		var cmt_form = $dom.find('form.act-cmt');
		cmt_form.ajaxForm({ 
	        dataType:  'json', 
	        beforeSubmit: function(formData, $form, options){
	        	$form.find('.grid_comment_button').hide();
	        	$form.find('.grid_comment_loading').show();
	        },
	        success:  function(data, status, xhr, $form){
	        	if(!data || data.resultCode != 'SUCCESS' ) return;
	        	op.get_cmt(data.resultData.id, 'pin', 
	        			$dom.find('.cmts'));
	        	$dom.find('form.act-cmt textarea').val('').blur();
	        	if($form.hasClass('fwd')){
	        		op.notify_header('转发成功');
	        	}else{
	        		op.notify_header('评论成功');
	        	}
	        	$form.attr('action', $form.attr('data-cmt-url')).removeClass('fwd');
	        },
	        complete: function(jqXHR, textStatus){
	        	cmt_form.find('.grid_comment_button').show();
	        	cmt_form.find('.grid_comment_loading').hide();
          	}
	    });
		$dom.find('form .grid_comment_button').click(function(){
			$(this).parents('form.act-cmt').submit();
		});
	},
	
	get_city_meta: function(pinyin, callback){
		$.getJSON( web_context + '/citymeta/' + pinyin, function(data){
			if(data && data.resultData){
				cityMeta = data.resultData;
				if(cityMeta.pinyin != pinyin){
					cityMeta.zoom = 12;
				}
				callback(cityMeta);
			}
		});
	},
	
	show_place_map: function(holder, no_marker, lng, lat, cityMeta){
		if(!no_marker){
			holder.gmap3({ 
				action: 'addMarker',
				latLng: new google.maps.LatLng(lng,lat),
				map:{
					center: true,
					zoom: cityMeta.zoom,
					scrollwheel: true,
					mapTypeId: google.maps.MapTypeId.ROADMAP,
					mapTypeControl: false,
					zoomControl: true,
					zoomControlOptions: {
						style : google.maps.ZoomControlStyle.SMALL
					},
					streetViewControl: false
				}
			});
		}else{
			holder.gmap3({ 
				action: 'init',
				options:{
					center: [cityMeta.lngLat[1], cityMeta.lngLat[0]],
					zoom: cityMeta.zoom,
					scrollwheel: true,
					mapTypeId: google.maps.MapTypeId.ROADMAP,
					mapTypeControl: false,
					zoomControl: true,
					zoomControlOptions: {
						style : google.maps.ZoomControlStyle.SMALL
					},
					streetViewControl: false
				}
			});
		}
	},
	
	show_name_card: function(uid, callback){
		$.get( web_context + '/profiles/' + uid + '/namecard', function(html){
			if(html){
				callback(html);
			}
		});
	},
	
	act_bind_event: function($dom){
		var form = $dom.find('form');
		form.ajaxForm({ 
	        dataType:  'json', 
	        beforeSubmit: function(formData, $form, options){
	        	$form.find('.grid_comment_button').hide();
	        	$form.find('.grid_comment_loading').show();
	        },
	        success:  function(data, status, xhr, $form){
	        	if(!data || data.resultCode != 'SUCCESS' ) return;
	        	if($form.hasClass('cmt')){
	        		op.get_cmt(data.resultData.id, 'dsb', 
		        			$dom.find('.cmts'));
	        		op.notify_header('评论成功');
	        	}else if($form.hasClass('fwd')){
	        		op.get_fwd(data.resultData.id, 'dsb', 
		        			$dom.find('.cmts'));
	        		op.notify_header('转发成功');
	        	}else if($form.hasClass('rep')){
	        		op.get_cmt(data.resultData.id, 'dsb', 
		        			$dom.find('.cmts'));
	        		op.notify_header('回复成功');
	        	}
	        	$form.find('textarea').val('').blur();
	        },
	        complete: function(jqXHR, textStatus){
	        	form.find('.grid_comment_button').show();
	        	form.find('.grid_comment_loading').hide();
          	}
	    });
		$dom.find('form.fwd textarea, form.rep textarea').focus(function(){
			if(!$.trim($(this).val())){
				var basedOn = $(this).attr('data-based-on');
				if(basedOn){
					$(this).val(basedOn);
				}else{
					$(this).val('转发八卦:');
				}
			}
		});
		$dom.find('form.fwd textarea, form.rep textarea').blur(function(){
			var basedOn = $(this).attr('data-based-on');
			if($.trim($(this).val()) == $.trim(basedOn)){
				$(this).val('');
			}
		});
		$dom.find('form .grid_comment_button').click(function(){
			$(this).parents('form').submit();
		});
	},
	
	get_reps_dashboard: function(actId, no, c_dom){
		var loading_box = $(c_dom).find('.loading-box');
		loading_box.show();
		setTimeout(function(){
			$(c_dom).parents('.fwds-box')
				.data('first_load', true);
			$(c_dom).show();
			loading_box.hide();
		}, 1000);
	},
	
	get_fwds_dashboard: function(actId, no, c_dom){
		var loading_box = $(c_dom).find('.loading-box');
		$.ajax({
			url: web_context + '/activities/'+ actId + '/fwds/' + no,
			beforeSend: function(){
				loading_box.show();
			},
			success: function(data) {
				$(c_dom).show();
				if($.trim(data)){
					var cmt_e = $(data).appendTo(c_dom);
					cmt_e.find('.timeago').timeago();
				}
			},
			complete: function(){
				$(c_dom).parents('.fwds-box')
					.data('first_load', true);
				loading_box.hide();
			}
		});
	},
	
	get_cmts_dashboard: function(actId, no, c_dom){
		var loading_box = $(c_dom).find('.loading-box');
		$.ajax({
			url: web_context + '/activities/'+ actId + '/cmts/' + no,
			beforeSend: function(){
				loading_box.show();
			},
			success: function(data) {
				$(c_dom).show();
				if($.trim(data)){
					var cmt_e = $(data).appendTo(c_dom);
					cmt_e.find('.timeago').timeago();
				}
			},
			complete: function(){
				$(c_dom).parents('.cmts-box')
					.data('first_load', true);
				loading_box.hide();
			}
		});
	},
	
	get_cmt: function(cmtId, type, c_dom){
		$.ajax({
			url: web_context + '/ops/cmt/'+ cmtId + '/view/' + type,
			success: function(data) {
				$(c_dom).show();
				var cmt_e = $(data).prependTo(c_dom);
				cmt_e.find('.timeago').timeago();
				$(cmt_e).effect('highlight', {}, 1600);
				if($(c_dom).find('.empty-holder').length!=0){
					cmt_e.addClass('last');
					$(c_dom).find('.empty-holder').remove();
				}
				if(type=='pin'){
					$(c_dom).parents('.pin').parent().masonry('reload');
					$.scrollTo(cmt_e, 800, {
						offset: {
							top: -300
						},
						margin: true
					  }
					);
				}else if(type='dsb'){
					
				}
			}
		});
	},
	
	get_fwd: function(fwdId, type, c_dom){
		$.ajax({
			url: web_context + '/ops/fwd/'+ fwdId + '/view/' + type,
			success: function(data) {
				$(c_dom).show();
				var cmt_e = $(data).prependTo(c_dom);
				cmt_e.find('.timeago').timeago();
				$(cmt_e).effect('highlight', {}, 1600);
				$(c_dom).find('.empty-holder').hide();
				if(type=='pin'){
					$(c_dom).parents('.pin').parent().masonry('reload');
					$.scrollTo(cmt_e, 800, {
						offset: {
							top: -300
						},
						margin: true
					  }
					);
				}else if(type='dsb'){
					
				}
			}
		});
	},
	
	update_bin: function(container, bin){
		var data_type = bin.data_type;
		var data_value = bin.data_value; 
		var data_label = bin.data_label;
		var gender_bin = $('.bin strong[data-type="'+data_type+'"]').parent();
		if(gender_bin.length==0){
			gender_bin = $('#bin-template').clone().attr('id', '')
				.appendTo(container).show();
		}
		gender_bin.find('strong')
			.attr('data-type', data_type).attr('data-value', data_value)
			.text(data_label);
	},
	
	update_query_str: function(data){
		var params = data || {};
		var href = window.location.href;
		var idx1 = href.indexOf("#");
		var idx2 = href.indexOf("?");
		var idx = Math.min(idx1!=-1? idx1 : href.length, 
				idx2!=-1 ? idx2 : href.length);
		href = href.substring(0, idx);
		var new_href = href + '?' + $.param(params);
		window.location.href = new_href;
	},
	
	city_picker_geo_callback: function(city){
		$.getJSON(web_context+'/geocity/'+city, function(data){
			if(data && data.resultCode == 'SUCCESS'){
				var pinyin = data.resultData.pinyin;
				var w_href = window.location.href;
				var base_url = web_context, url;
				var map_idx = w_href.indexOf('/map');
				if(map_idx==-1){
					url = base_url;
				}else{
					url = w_href.substring(0, map_idx +4);
				}
				if(url.charAt(url.length-1) != '/'){
					url += '/';
				}
				window.location.href = url + '?city='+pinyin;
			}
		});
	},
	
	notify: function(text,refer,dir){
		var opts = {
			    pnotify_title: text
		    };
		if(refer){
			opts.pnotify_before_open = function(pnotify) {
				var refer_os = $(refer).offset();
				var win_st = $(window).scrollTop();
				if(dir=='br'){
					pnotify.css({
		                top: refer_os.top + $(refer).outerHeight() - win_st + 2 ,
		                left: refer_os.left + $(refer).outerWidth() - pnotify.outerWidth()
		            });
				}
	        };
		}
		return $.pnotify(opts);
	},
	
	noti_header: null,
	notify_header: function(text){
		if (typeof noti_header != "undefined") {
			noti_header.pnotify({
				pnotify_title: text
			});
			noti_header.pnotify_display();
	    }else{
	    	noti_header = op.notify(text, '.navbar-fixed-top .container', 'br');
	    }
	}
};