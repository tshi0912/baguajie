package net.baguajie.admin.viewhelper
{
	import com.adobe.cairngorm.view.ViewLocator;

	public class ViewHelperDelegator
	{
		public static function getAdminHelper():AdminHelper
		{
			var adminHelper:AdminHelper=ViewLocator.getInstance().getViewHelper("adminHelper") as AdminHelper;
			return adminHelper;
		}
	}
}
