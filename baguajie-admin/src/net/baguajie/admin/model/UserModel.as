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
	public class UserModel implements ModelLocator
	{
		private static var model:UserModel;
		
		public static function getInstance():UserModel
		{
			if (model == null)
			{
				model=new UserModel();
			}
			return model;
		}
		
		public function UserModel()
		{
			if (UserModel.model != null)
				throw new Error("Only one ModelLocator instance should be instantiated");
		}
		
		public var current:int=1;
		public var users:ArrayCollection=new ArrayCollection();
		public var total:int=1;
		
		public function getUsers():void
		{
			var token:SimpleROToken=SimpleROInvoker.getUsersAtPage(current-1);
			token.resultHandler=getUsersAtPageCompleteHandler;
		}
		
		private function getUsersAtPageCompleteHandler(users:ArrayCollection):void
		{
			if (users && users.length != 0)
			{
				this.users.source = users.source;
			}
			else
			{
				this.users.source = [];
				current = 1;
				total = 1;
			}
			this.users.refresh();
		}
	}
}