package net.baguajie.admin.model
{
	import com.adobe.cairngorm.model.ModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	import mx.controls.LinkBar;
	
	import net.baguajie.admin.controller.SimpleROInvoker;
	import net.baguajie.admin.controller.SimpleROToken;
	import net.baguajie.admin.event.SimpleROEvent;
	import net.baguajie.admin.vo.ErrorCodeVo;
	import net.baguajie.admin.vo.ResourceVo;
	import net.baguajie.admin.vo.SpotVo;
	import net.baguajie.admin.vo.UserVo;

	[Bindable]
	public class SpotModel implements ModelLocator
	{
		private static var model:SpotModel;
		private var user:UserVo;
		private var res:ResourceVo;
		private var spot:SpotVo;
		private var errorCode:ErrorCodeVo;

		public static function getInstance():SpotModel
		{
			if (model == null)
			{
				model=new SpotModel();
			}
			return model;
		}

		public function SpotModel()
		{
			if (SpotModel.model != null)
				throw new Error("Only one ModelLocator instance should be instantiated");
		}

		public var current:int=1;
		public var spots:ArrayCollection=new ArrayCollection();
		public var total:int=1;
		

		public function getSpots():void
		{
			var token:SimpleROToken=SimpleROInvoker.getSpotsAtPage(current-1);
			token.resultHandler=getSpotsAtPageCompleteHandler;
		}

		private function getSpotsAtPageCompleteHandler(spots:ArrayCollection):void
		{
			if (spots && spots.length != 0)
			{
				this.spots.source = spots.source;
			}
			else
			{
				this.spots.source = [];
				current = 1;
				total = 1;
			}
			this.spots.refresh();
		}
	}
}
