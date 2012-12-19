package net.baguajie.admin.viewhelper
{
	import com.adobe.cairngorm.view.ViewHelper;

	public class AdminHelper extends ViewHelper
	{
		public function AdminHelper()
		{
			super();
		}

		public function toLoginState():void
		{
			view.currentState="login";
		}

		public function toDefaultState():void
		{
			view.currentState="default";
		}
	}
}
