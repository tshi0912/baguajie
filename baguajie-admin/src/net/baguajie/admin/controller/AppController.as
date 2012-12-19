package net.baguajie.admin.controller
{
	import com.adobe.cairngorm.control.FrontController;
	import net.baguajie.admin.command.MultipleROCommand;
	import net.baguajie.admin.command.SimpleROCommand;
	import net.baguajie.admin.event.MultipleROEvent;
	import net.baguajie.admin.event.SimpleROEvent;

	public class AppController extends FrontController
	{
		public function AppController()
		{
			super();
			addCommand(SimpleROEvent.SIMPLE_RO_EVENT, SimpleROCommand);
			addCommand(MultipleROEvent.MULTIPLE_RO_EVENT, MultipleROCommand);
		}
	}
}
