package net.baguajie.admin.model
{
	import com.adobe.cairngorm.model.ModelLocator;
	
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	import mx.controls.Alert;
	import mx.controls.LinkBar;
	import mx.managers.BrowserManager;
	import mx.managers.IBrowserManager;
	import mx.utils.URLUtil;
	
	import net.baguajie.admin.controller.SimpleROInvoker;
	import net.baguajie.admin.controller.SimpleROToken;
	import net.baguajie.admin.viewhelper.ViewHelperDelegator;
	import net.baguajie.admin.vo.UserVo;

	[Bindable]
	public class AdminModel implements ModelLocator
	{
		private static var model:AdminModel;

		public static function getInstance():AdminModel
		{
			if (model == null)
			{
				model = new AdminModel();
				model.init();
			}
			return model;
		}

		public function AdminModel()
		{
			if (AdminModel.model != null)
				throw new Error("Only one ModelLocator instance should be instantiated");
		}
		
		public var baseUrl:String;
		public var bodyStack:ViewStack;
		public var menuBar:LinkBar;
		public var signInUser:UserVo;
		
		private function init():void
		{
			var browserManager:IBrowserManager = BrowserManager.getInstance();
			browserManager.init();
			var base:String = browserManager.base;
			var idx:int = base.indexOf("/admin");
			if(idx==-1){
				idx = base.lastIndexOf("/");
			}
			baseUrl = base.substring(0, idx);
		}
		
		public function signIn(name:String, pwd:String):void
		{
			var token:SimpleROToken=SimpleROInvoker.signIn(name, pwd);
			token.resultHandler=signInResultHandler;
		}
		
		public function signInResultHandler(signInUser:UserVo):void
		{
			this.signInUser = signInUser;
			ViewHelperDelegator.getAdminHelper().toDefaultState();
		}
	}
}
