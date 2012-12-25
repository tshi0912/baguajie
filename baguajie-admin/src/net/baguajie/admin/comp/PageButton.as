package net.baguajie.admin.comp
{
	import spark.components.Button;
	
	public class PageButton extends Button
	{
		public function PageButton()
		{
			super();
			buttonMode = true;
		}
		
		private var _selected:Boolean;
		
		public function set selected(value:Boolean):void
		{
			if(_selected != value)
			{
				_selected = value;
				invalidateSkinState();
			}
		}
		
		/**
		 *  @private
		 */
		override protected function getCurrentSkinState():String
		{
			if(_selected)
				return "selected";
			else
				return super.getCurrentSkinState();
		}
	}
}