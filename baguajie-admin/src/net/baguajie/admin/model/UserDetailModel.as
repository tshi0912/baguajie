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
	import mx.utils.URLUtil;
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

		public var imageHeight:int;
		public var imageUrl:String;
		public var imageWidth:int=190;
		public var roleIdx:int=-1;
		public var roles:ArrayCollection=new ArrayCollection([UserVo.USER, UserVo.ADMIN]);
		public var status:ArrayCollection=new ArrayCollection([UserVo.VALID, UserVo.INVALID]);
		public var statusIdx:int=-1;
		private var _orgUser:UserVo;
		private var _user:UserVo;

		private var view:UserDetailPopup;

		public function showPopup(user:UserVo, parentView:UIComponent):void
		{
			this.user=user;
			if (!view)
			{
				view=new UserDetailPopup();
			}
			if (!view.parent)
			{
				PopUpManager.addPopUp(view, parentView.parentApplication.systemManager, true);
				PopUpManager.centerPopUp(view);
			}
		}

		public function update():void
		{
			if (_orgUser.status != user.status || _orgUser.role != user.role)
			{
				var token:SimpleROToken=SimpleROInvoker.updateUser(user);
				token.resultHandler=updateUserCompleteHandler;
			}
			else
			{
				view.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
			}
		}

		public function get user():UserVo
		{
			return _user;
		}

		public function set user(value:UserVo):void
		{
			_user=ObjectUtil.copy(value) as UserVo;
			_orgUser=ObjectUtil.copy(value) as UserVo;
			roleIdx=user.role ? roles.getItemIndex(user.role) : -1;
			statusIdx=user.status ? status.getItemIndex(user.status) : -1;
			if (user.avatar && user.avatar.orgSize && user.avatar.orgSize.length == 2)
			{
				var w:int=_user.avatar.orgSize[1];
				var h:int=_user.avatar.orgSize[0];
				imageHeight=h * 190 / w;
				imageUrl=AdminModel.getInstance().baseUrl + '/images/avatars/' + _user.avatar.resId;
			}
			else
			{
				imageHeight=0;
				imageUrl=null;
			}
		}

		private function updateUserCompleteHandler(user:UserVo):void
		{
			ObjectCopyUtil.mergeUpdateList(new ArrayCollection([user]), UserModel.getInstance().users, "id");
			view.dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
	}
}
