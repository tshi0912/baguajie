package net.baguajie.admin.model
{
	import com.adobe.cairngorm.model.ModelLocator;
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	import mx.controls.LinkBar;

	[Bindable]
	public class AdminModel implements ModelLocator
	{
		private static var model:AdminModel;

		public static function getInstance():AdminModel
		{
			if (model == null)
			{
				model = new AdminModel();
			}
			return model;
		}

		public function AdminModel()
		{
			if (AdminModel.model != null)
				throw new Error("Only one ModelLocator instance should be instantiated");
		}

		public var bodyStack:ViewStack;
		public var menuBar:LinkBar;
	}
}
