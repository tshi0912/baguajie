package net.baguajie.admin.model
{
	import com.adobe.cairngorm.model.ModelLocator;
	
	import flash.display.DisplayObject;
	
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	import mx.controls.LinkBar;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.utils.ObjectUtil;
	
	import net.baguajie.admin.controller.SimpleROInvoker;
	import net.baguajie.admin.controller.SimpleROToken;
	import net.baguajie.admin.event.SimpleROEvent;
	import net.baguajie.admin.util.ObjectCopyUtil;
	import net.baguajie.admin.view.UserDetailPopup;
	import net.baguajie.admin.vo.UserVo;
	
	import spark.components.Application;
	
	[Bindable]
	public class UserDetailModel implements ModelLocator
	{
		private static var model:UserDetailModel;
		
		public static function getInstance():UserDetailModel
		{
			if (model == null)
			{
				model=new UserDetailModel();
			}
			return model;
		}
		
		public function UserDetailModel()
		{
			if (UserDetailModel.model != null)
				throw new Error("Only one ModelLocator instance should be instantiated");
		}
		
		private var view:UserDetailPopup;
		private var _user:UserVo;
		private var _orgUser:UserVo;
		
		public function get user():UserVo{
			return _user;
		}
		
		public function set user(value:UserVo):void{
			_user = ObjectUtil.copy(value) as UserVo;
			_orgUser = ObjectUtil.copy(value) as UserVo;
		}
		
		public function showPopup(user:UserVo, parentView:UIComponent):void{
			this.user = user;
			if(!view){
				view = new UserDetailPopup();
			}
			if(!view.parent){
				PopUpManager.addPopUp(view, parentView.parentApplication.systemManager, true);
				PopUpManager.centerPopUp(view);
			}
		}
		
		public function update():void
		{
			if (_orgUser.status != user.status)
			{
				var token:SimpleROToken=SimpleROInvoker.updateUserStatus(user.id, user.status);
				token.resultHandler=updateUserStatusCompleteHandler;
			}
			else
			{
				view.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
			}
		}
		
		private function updateUserStatusCompleteHandler(user:UserVo):void
		{
			ObjectCopyUtil.mergeUpdateList(new ArrayCollection([user]), UserModel.getInstance().users, "id");
			view.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
	}
}