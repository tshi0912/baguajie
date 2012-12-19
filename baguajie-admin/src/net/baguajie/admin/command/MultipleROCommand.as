package net.baguajie.admin.command
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import net.baguajie.admin.controller.MultipleROToken;
	import net.baguajie.admin.event.MultipleROEvent;

	public class MultipleROCommand extends SimpleROCommand
	{

		public function MultipleROCommand()
		{
			super();
		}

		private var multipleROToken:MultipleROToken;

		override public function execute(event:CairngormEvent):void
		{
			super.execute(event);

			multipleROToken=MultipleROToken(MultipleROEvent(event).token);
		}

		override public function result(event:Object):void
		{
			super.result(event);

			if (multipleROToken.defaultResultHandler != null)
				multipleROToken.defaultResultHandler();
		}
	}
}
