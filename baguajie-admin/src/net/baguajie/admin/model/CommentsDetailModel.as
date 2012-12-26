package net.baguajie.admin.model
{
	import mx.collections.ArrayCollection;
	import mx.core.UIComponent;
	import mx.managers.PopUpManager;
	
	import net.baguajie.admin.controller.SimpleROInvoker;
	import net.baguajie.admin.controller.SimpleROToken;
	import net.baguajie.admin.util.ObjectCopyUtil;
	import net.baguajie.admin.view.CommentsDetailPopup;
	import net.baguajie.admin.vo.CommentVo;
	import net.baguajie.admin.vo.SpotVo;

	[Bindable]
	public class CommentsDetailModel
	{
		private static var model:CommentsDetailModel;
		
		public static function getInstance():CommentsDetailModel
		{
			if (model == null)
			{
				model=new CommentsDetailModel();
			}
			return model;
		}
		
		public function CommentsDetailModel()
		{
			if (CommentsDetailModel.model != null)
				throw new Error("Only one ModelLocator instance should be instantiated");
		}
		
		public var comments:ArrayCollection = new ArrayCollection();
		public var status:ArrayCollection=new ArrayCollection([CommentVo.VALID, CommentVo.INVALID]);
		private var view:CommentsDetailPopup;
		
		public function showPopup(spot:SpotVo, parentView:UIComponent):void
		{
			comments.source = [];
			comments.refresh();
			var token:SimpleROToken=SimpleROInvoker.getComments(spot.id, spot.createdBy.id);
			token.resultHandler=getCommentsCompleteHandler;
			if (!view)
			{
				view=new CommentsDetailPopup();
			}
			if (!view.parent)
			{
				PopUpManager.addPopUp(view, parentView.parentApplication.systemManager, true);
				PopUpManager.centerPopUp(view);
			}
		}
		
		private function getCommentsCompleteHandler(comments:ArrayCollection):void
		{
			if(comments)
			{
				this.comments.source = comments.source;
			}
			this.comments.refresh();
		}
		
		public function updateStatus(item:CommentVo):void
		{
			var token:SimpleROToken=SimpleROInvoker.updateCommentStatus(item.id, item.status);
			token.resultHandler=updateCommentStatusResultHandler;
		}
		
		private function updateCommentStatusResultHandler(item:CommentVo):void
		{
			ObjectCopyUtil.mergeUpdateList(new ArrayCollection([item]), comments, "id");
		}
	}
}