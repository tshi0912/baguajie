/**
 * pop out info panel model
 */
if(typeof google ==="undefined"){
	//alert("google map not downloaded yet!");
}
else{
	MarkerOverlayManager = {
		zCounter:1,
		infoPanel:{
			id:"marker-tip",
			imgWrapId:"mt_img_a",
			imgId:"mt_img",
			nameId:"mt_name",
			addressId:"mt_address",
			descId:"mt_desc"
		},
		flagDown:false,
		mapOpt:{
			zoom:4,
			center:new google.maps.LatLng(34.0096,115.0972),
			mapTypeId:google.maps.MapTypeId.ROADMAP,
			mapTypeControl: false,
			scrollwheel :false,
			streetViewControl: false
		},
		mapDivId:"explore-map",
		myMap: null,
		markers: [],
		timer: null,
		initialMap:function(){
//			var _mapDivId = this.mapDivId;
//			if(BC.Lang.trim(_mapDivId) == ""){
//				alert("map div id no initialled");
//				return null;
//			}
//			if(!this.myMap){
//				this.myMap = new google.maps.Map(G(_mapDivId),this.mapOpt);
//			}
			google.maps.event.addListener(this.myMap, "dragstart", function(){
				MarkerOverlayManager.flagDown = true;
				var _MinfoPanel = $('#'+MarkerOverlayManager.infoPanel.id);
				_MinfoPanel.hide();
			});
			google.maps.event.addListener(this.myMap, "dragend", function(){
				MarkerOverlayManager.flagDown = false;
			});
		},
		draw:function(dataArr, append){
			if(!dataArr){ return;}
			if(!this.myMap){return;}
			this.initialMap();
			var _len = dataArr.length;
			//clear the already exist markers...when page change
			if(!append){
				this.remove();
				this.markers = [];
			}
			if(!this.markers){
				this.markers = [];
			}
			var s = this.markers.length;
			for(var i=0;i<_len;i++){
				this.markers[s+i]=new MarkerOverlay(dataArr[i]);
			}
		},
		remove:function(){
			var m_len=this.markers.length;
			for(var j=0;j<m_len;j++){
				this.markers[j].setMap(null);
			}
		}
	};
}