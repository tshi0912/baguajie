package net.baguajie.admin.model
{
	import com.adobe.cairngorm.model.ModelLocator;
	import flash.display.DisplayObject;
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	import mx.controls.LinkBar;
	import mx.core.INavigatorContent;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.utils.ObjectUtil;
	import mx.utils.URLUtil;
	import net.baguajie.admin.controller.SimpleROInvoker;
	import net.baguajie.admin.controller.SimpleROToken;
	import net.baguajie.admin.event.SimpleROEvent;
	import net.baguajie.admin.util.ObjectCopyUtil;
	import net.baguajie.admin.view.SpotDetailPopup;
	import net.baguajie.admin.view.UserDetailPopup;
	import net.baguajie.admin.vo.SpotVo;
	import spark.components.Application;

	[Bindable]
	public class SpotDetailModel implements ModelLocator
	{
		private static var model:SpotDetailModel;

		public static function getInstance():SpotDetailModel
		{
			if (model == null)
			{
				model=new SpotDetailModel();
			}
			return model;
		}

		public function SpotDetailModel()
		{
			if (SpotDetailModel.model != null)
				throw new Error("Only one ModelLocator instance should be instantiated");
		}

		public var imageHeight:int;
		public var imageUrl:String;
		public var imageWidth:int=190;
		public var status:ArrayCollection=new ArrayCollection([SpotVo.VALID, SpotVo.INVALID]);
		public var statusIdx:int=-1;
		private var _orgSpot:SpotVo;
		private var _spot:SpotVo;

		private var view:SpotDetailPopup;

		public function showPopup(spot:SpotVo, parentView:UIComponent):void
		{
			this.spot=spot;
			if (!view)
			{
				view=new SpotDetailPopup();
			}
			if (!view.parent)
			{
				PopUpManager.addPopUp(view, parentView.parentApplication.systemManager, true);
				PopUpManager.centerPopUp(view);
			}
		}

		public function get spot():SpotVo
		{
			return _spot;
		}

		public function set spot(value:SpotVo):void
		{
			_spot=ObjectUtil.copy(value) as SpotVo;
			_orgSpot=ObjectUtil.copy(_spot) as SpotVo;
			statusIdx=spot.status ? status.getItemIndex(spot.status) : -1;
			if (_spot.image && spot.image.orgSize && spot.image.orgSize.length == 2)
			{
				var w:int=_spot.image.orgSize[1];
				var h:int=_spot.image.orgSize[0];
				imageHeight=h * 190 / w;
				imageUrl=AdminModel.getInstance().baseUrl + '/images/spots/' + _spot.image.resId;
			}
		}

		public function update():void
		{
			if (_orgSpot.status != spot.status)
			{
				var token:SimpleROToken=SimpleROInvoker.updateSpotStatus(spot.id, spot.status);
				token.resultHandler=updateSpotStatusCompleteHandler;
			}
			else
			{
				view.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
			}
		}

		private function updateSpotStatusCompleteHandler(spot:SpotVo):void
		{
			ObjectCopyUtil.mergeUpdateList(new ArrayCollection([spot]), SpotModel.getInstance().spots, "id");
			view.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
	}
}
