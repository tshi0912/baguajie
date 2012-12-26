package net.baguajie.admin.model
{
	import com.adobe.cairngorm.model.ModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	import mx.controls.LinkBar;
	
	import net.baguajie.admin.controller.SimpleROInvoker;
	import net.baguajie.admin.controller.SimpleROToken;
	import net.baguajie.admin.event.SimpleROEvent;
	import net.baguajie.admin.util.Constants;
	import net.baguajie.admin.vo.PageVo;

	[Bindable]
	public class SpotModel implements ModelLocator
	{
		private static var model:SpotModel;

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
		public var sizePerPage:int = Constants.PAGE_SIZE_25;

		public function getSpots():void
		{
			var token:SimpleROToken=SimpleROInvoker.getSpotsAtPage(current-1, sizePerPage);
			token.resultHandler=getSpotsAtPageCompleteHandler;
		}

		private function getSpotsAtPageCompleteHandler(page:PageVo):void
		{
			if (page && page.content && page.content is ArrayCollection)
			{
				this.spots.source = (page.content as ArrayCollection).source;
				current = page.number+1;
				total = page.totalPages;
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
