/**
 * customize the google map overlay Class
 */
if(typeof google ==="undefined"){
	//alert("google map not downloaded yet!");
}
else{
	MarkerOverlay = function(data){
		if(!google){alert("google maps not download yet!");}
		this.latlng_=new google.maps.LatLng(data.lngLat[1],data.lngLat[0]);
		this.div_=null;
		this.isMulti = false;
		this.markerVo = data;
		this.crtIdx = 0;
		this.crtMarker = data;
		this.setMap(MarkerOverlayManager.myMap);
	};
	
	//initial the prototype and functions of this class
	MarkerOverlay.prototype=new google.maps.OverlayView();
	
	MarkerOverlay.prototype.onAdd =function(){
		var _div=document.createElement("DIV");
		var _a = document.createElement("a");
		var _img=document.createElement("img");
		_img.src=this.crtMarker.imageUrl;
		_a.appendChild(_img);
		_div.appendChild(_a);
		this.div_=_div;
		var panes=this.getPanes();
		panes.overlayImage.appendChild(_div);
		//append the css style to the lay
		$(_a).addClass('map-marker-a img');
		if(!this.isMulti){
			$(_div).addClass('map-marker');
		}
	};
	
	MarkerOverlay.prototype.show=function(){
		var _M = MarkerOverlayManager;
		var _MinfoPanel = $('#'+_M.infoPanel.id)[0];
		var overlayProjection=this.getProjection();
		var pixPosition=overlayProjection.fromLatLngToContainerPixel(this.latlng_);
		//set up the content of popup div
		_MinfoPanel.style.display="block";
		$('#'+_M.infoPanel.imgId).attr('src', this.crtMarker.imageUrl);
		$('#'+_M.infoPanel.imgWrapId).attr('href', web_context+'/spots/'+
					this.crtMarker.spotId);
		$('#'+_M.infoPanel.nameId).html(this.crtMarker.name);
		$('#'+_M.infoPanel.addressId).html(this.crtMarker.placeAddr);
		$('#'+_M.infoPanel.descId).html(this.crtMarker.summary);
		$('#'+_M.infoPanel.id).data('spotId', this.crtMarker.spotId);
		//set up the position of popup div
		var _height=_MinfoPanel.offsetHeight;
		var _adjustY = -55;
		var _adjustX = 0;
		if(this.isMulti){
			_adjustY = -21;
			_adjustX = 5;
		}
		_MinfoPanel.style.left=pixPosition.x + _adjustX - 150 + "px";
		_MinfoPanel.style.top=pixPosition.y + _adjustY - _height+"px";
		this.div_.children[0].src = this.crtMarker.imageUrl;
	};
	
	MarkerOverlay.prototype.move=function(){
		var _M = MarkerOverlayManager;
		var _MinfoPanel = $('#'+_M.infoPanel.id)[0];
		var overlayProjection=this.getProjection();
		var pixPosition=overlayProjection.fromLatLngToContainerPixel(this.latlng_);
		var _height=_MinfoPanel.offsetHeight;
		var _adjustY = -55;
		var _adjustX = 0;
		if(this.isMulti){
			_adjustY = -21;
			_adjustX = 5;
		}
		_MinfoPanel.style.left=pixPosition.x + _adjustX - 150 + "px";
		_MinfoPanel.style.top=pixPosition.y + _adjustY - _height+"px";
	};

	MarkerOverlay.prototype.draw=function(){
		var me=this;

		var overlayProjection=this.getProjection();
		var pixPosition=overlayProjection.fromLatLngToDivPixel(this.latlng_);
		this.div_.style.left =(pixPosition.x-23) + "px";
		this.div_.style.top = (pixPosition.y-55) + "px";
		
		var _M = MarkerOverlayManager;
		var _MinfoPanel = $('#'+_M.infoPanel.id)[0];
		
		//add Event: mouseover
		google.maps.event.addDomListener(this.div_,'mouseover',function(event){   
			this.style.zIndex = ++_M.zCounter;
			clearTimeout(_M.timer);
			me.show();
		});
		
		//add Event: mouseout
		google.maps.event.addDomListener(this.div_,'mouseout',function(){
			clearTimeout(_M.timer);
			_M.timer = setTimeout(function(){
				_MinfoPanel.style.display="none";
			}, 3000);
		});
		
		//add Event: click
		google.maps.event.addDomListener(this.div_,'click',function(){
//			me.crtIdx >= me.markerVo.data.length-1 ? me.crtIdx = 0 : me.crtIdx ++;
//			me.crtMarker = me.markerVo.data[me.crtIdx];
			me.show();
		});
		
		//add Event: dblclick
		google.maps.event.addDomListener(this.div_,'dblclick',function(event){
			if (event.stopPropagation) {
				// this code is for Mozilla and Opera
				event.stopPropagation();
			}else if (window.event) {
				// this code is for IE
				window.event.cancelBubble = true;
			}
		});
	};

	MarkerOverlay.prototype.onRemove=function(){
		this.div_.parentNode.removeChild(this.div_);
		this.div_=null;
	};

}
