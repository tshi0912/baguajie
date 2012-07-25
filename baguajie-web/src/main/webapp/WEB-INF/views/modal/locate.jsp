<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div id="place-locate-modal" class="modal hide" 
		style="">
	<c:url value="/places/create" var="cpf_url"/> 
	<form:form id="create-place-form" action="${cpf_url}" 
		cssClass="sign-in" modelAttribute="placeCreationVo" method="post" >
	<div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h3>地址定位</h3>
	</div>
	<div class="modal-body upload">
		<div id="place-locate-map" style="width: 530px; height: 220px"></div>
		<div class="">
				<input id="place-id-modal-hid" type="hidden" />
				<input id="place-address-nation-hid" name="address[nation]" type="hidden" />
				<input id="place-address-province-hid" name="address[province]" type="hidden" />
				<input id="place-address-city-hid" name="address[city]" type="hidden" />
				<input id="place-address-district-hid" name="address[district]" type="hidden" />
				<input id="place-address-street-hid" name="address[street]" type="hidden" />
				<input id="place-address-zipCode-hid" name="address[zipCode]" type="hidden" />
				<div class="row-fluid mt-10">
					<input class="span10" id="place-full-addr" name="fullAddr" type="text" 
						placeholder="拖动上方图钉,自动获得地址" autocomplete="off" />
					<div class="span2 offset1">
						<form:errors path="fullAddr" cssClass="alert alert-error" /></div>
				</div>
				<div class="row-fluid">
					<input class="span10" id="place-lng-lat" name="lngLat" type="text" 
						placeholder="拖动上方图钉,自动获得经纬度" autocomplete="off" />
					<div class="span1 offset1">
						<form:errors path="lngLat" cssClass="alert alert-error" /></div>
				</div>
		</div>
	</div>
	<div class="modal-footer">
		<input type="submit" class="btn btn-primary btn-large" value="确定"></input>
	</div>
	</form:form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('#place-locate-modal').on('shown', function () {
			var locateObj = window.getLocateObj();
			if(locateObj){
				$('#place-full-addr').val(locateObj.fullAddr);
				$('#place-lng-lat').val(locateObj.lngLat);
				$('#place-id-modal-hid').val(locateObj.placeId);
			}
			var map = $('#place-locate-map').gmap3('get');
			if(!map){
				// initialize google map
				$.getJSON( '<c:url value="/citymeta/" />', function(data){
					if(data && data.resultData){
						var cityMeta = data.resultData;
						$('#place-locate-map').gmap3({
							action: 'init',
							options:{
								center: [cityMeta.lngLat[1], cityMeta.lngLat[0]],
								zoom: cityMeta.zoom,
								scrollwheel: true,
								mapTypeId: google.maps.MapTypeId.ROADMAP,
								mapTypeControl: false,
								zoomControl: true,
								zoomControlOptions: {
									style : google.maps.ZoomControlStyle.SMALL,
								},
								streetViewControl: false
							},
							events: {
								zoom_changed: function(zoom){
									var marker = $(this).gmap3({action:'get',
										name:'marker', first: true});
									if(!marker ) return;
									$(this).gmap3({
										action: 'panTo',
										args: [marker.getPosition()]
									});
								}
							},
							callback: function(){
								$('#place-lng-lat').data('latLng', [cityMeta.lngLat[1],cityMeta.lngLat[0]]);
								locate($('#place-full-addr').val());
							}
						});
					}
				});
			}else{
				locate($('#place-full-addr').val());
			}
	    });
		
		function locate(address){
			// clear marker first
			$('#place-locate-map').gmap3({
				action: 'clear',
				name: ['marker']
			});
			var latLng = $('#place-lng-lat').data('latLng');
			if($.trim(address) != ''){
				// firstly, get geometry from address
				$('#place-locate-map').gmap3({
					action: 'getLatLng',
					address: $.trim(address),
					callback: function(results){
						addMarker($(this), [results[0].geometry.location.lat(),
								results[0].geometry.location.lng()]);
					}
				});
			}else if(latLng){
				addMarker($('#place-locate-map'), latLng);
			}else{
				return;
			}
			
		}
		
		function addMarker($this, latLng){
			// add marker
			$this.gmap3({
				action: 'addMarker',
				latLng: latLng,
				marker: {
					options:{
						draggable: true,
						animation: google.maps.Animation.DROP,
						icon: '<c:url value="/resources/img/map_pin.png" />'
					},
					events:{
						// track latlng
						drag: function(marker, event, data){
							$('#place-lng-lat').val(event.latLng.lng()+
									","+event.latLng.lat());
							$('#place-lng-lat').data('latLng', [event.latLng.lat(),
									event.latLng.lng()]);
						},
						// get address
						dragend: function(marker, event, data){
							$(this).gmap3({
								action: 'getAddress',
								latLng: marker.getPosition(),
								callback:function(results){
									$('#place-full-addr').val(results[0]
										.formatted_address);
									$('#place-id-modal-hid').val('');
									$('#place-full-addr').data('address', 
										formatAddress(results[0]));
								}
							});
						}
					}
				}
			});
		}
		
		function formatAddress(geocoderResult){
			var address = {};
			var addr_components = geocoderResult.address_components,
			addr_component, types;
			for(var i=addr_components.length-1; i>=0; i--){
				addr_component = addr_components[i];
				types = addr_component.types.join(',');
				if(types.indexOf('postal_code')!=-1){
					address.zipCode = addr_component.long_name;
					$('#place-address-zipCode-hid').val(addr_component.long_name);
				}else if(types.indexOf('country')!=-1){
					address.nation = addr_component.long_name;
					$('#place-address-nation-hid').val(addr_component.long_name);
				}else if(types.indexOf('administrative_area_level_1')!=-1){
					address.province = addr_component.long_name;
					$('#place-address-province-hid').val(addr_component.long_name);
				}else if(types.indexOf('sublocality')!=-1){
					address.district = addr_component.long_name;
					$('#place-address-district-hid').val(addr_component.long_name);
				}else if(types.indexOf('locality')!=-1){
					address.city = addr_component.long_name;
					$('#place-address-city-hid').val(addr_component.long_name);
				}else if(types.indexOf('route')!=-1){
					address.street = addr_component.long_name;
					$('#place-address-street-hid').val(addr_component.long_name);
				}else if(types.indexOf('street_number')!=-1){
					address.street = address.street + addr_component.long_name;
					$('#place-address-street-hid').val($('#place-address-street-hid').val()
							+ addr_component.long_name);
				}else{
					;//other ignore
				}
			}
			return address;
		}
		
		$('#create-place-form').ajaxForm({ 
	        dataType:  'json', 
	        beforeSubmit: function(formData, jqForm, options){
	        	var placeId = $('#place-id-modal-hid').val();
	        	if(placeId){
	        		$('#place-locate-modal').modal('hide');
	        		return false;
	        	}
	        },
	        success:  function(data){
	        	if(!data || data.resultCode != 'SUCCESS' ) return;
	        	$('#place-locate-modal').modal('hide');
	        	window.locateCallback(data.resultData);
	        },
	        complete: function(jqXHR, textStatus){
          		
          	}
	    });
		
		/*$('#place-locate-modal .btn-primary').click(function(){
			$('#create-place-form').ajaxSubmit();
		});*/
	});
</script>