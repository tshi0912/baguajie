package net.baguajie.admin.command
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import mx.rpc.events.ResultEvent;
	import net.baguajie.admin.controller.ROResult;
	import net.baguajie.admin.controller.SimpleROToken;
	import net.baguajie.admin.event.SimpleROEvent;

	public class SimpleROCommand extends BaseCommand
	{

		public function SimpleROCommand()
		{
			super();
		}

		private var simpleROToken:SimpleROToken;

		override public function execute(event:CairngormEvent):void
		{
			var simpleROEvent:SimpleROEvent=SimpleROEvent(event);
			var newArgs:Array=[simpleROEvent.methodName];
			if (simpleROEvent.args && simpleROEvent.args.length > 0)
			{
				newArgs=newArgs.concat(simpleROEvent.args);
			}

			sendRemoteCall.apply(null, newArgs);

			simpleROToken=simpleROEvent.token;
		}

		override public function fault(event:Object):void
		{
			if (simpleROToken.faultHandler != null)
			{
				simpleROToken.faultHandler();
			}
		}

		override public function finallyExecute(event:Object):void
		{
			if (simpleROToken.finalHandler != null)
			{
				simpleROToken.finalHandler();
			}
		}

		override public function result(event:Object):void
		{
			if (simpleROToken.resultHandler != null)
			{
				simpleROToken.resultHandler(ROResult(ResultEvent(event).result).result);
			}
		}
	}
}
