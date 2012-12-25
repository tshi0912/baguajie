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
	import net.baguajie.admin.vo.ErrorCodeVo;
	import net.baguajie.admin.vo.PageVo;
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
		public var sizePerPage:int = Constants.PAGE_SIZE_25;
		
		public function getUsers():void
		{
			var token:SimpleROToken=SimpleROInvoker.getUsersAtPage(current-1, sizePerPage);
			token.resultHandler=getUsersAtPageCompleteHandler;
		}
		
		private function getUsersAtPageCompleteHandler(page:PageVo):void
		{
			if (page && page.content && page.content is ArrayCollection)
			{
				this.users.source = page.content.source;
				current = page.number+1;
				total = page.totalPages;
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