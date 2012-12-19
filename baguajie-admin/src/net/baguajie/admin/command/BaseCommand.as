package net.baguajie.admin.command
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import net.baguajie.admin.util.ErrorHandleUtil;

	public class BaseCommand implements ICommand, IResponder
	{
		private static const TRACE_EXCUTING_TIME:Boolean=true;

		public function BaseCommand()
		{

		}

		protected var serviceId:String="adminRO";

		private var executingStartTime:Number;
		private var methodName:String;

		public function execute(event:CairngormEvent):void
		{
//			to be overridden by subclasses
//			prepare necessary arguments for the remote call
//			and then use the method "sendRemoteCall" to do the actual remote calling
		}

		public function fault(event:Object):void
		{
//			 to be overridden by subclasses
		}

		public function finallyExecute(event:Object):void
		{
//			 to be overridden by subclasses
		}

		public function result(event:Object):void
		{
//			to be overridden by subclasses
		}

		protected function faultHandler(event:Object):void
		{
			if (!event || !event.fault)
				return;

			trace("Command \"" + methodName + "\" failed.");
			trace(FaultEvent(event).message);
			trace(FaultEvent(event).fault);
			
			fault(event);

			finallyExecute(event);
		}

		protected function resultHandler(event:Object):void
		{
			if (TRACE_EXCUTING_TIME)
				trace("Method \"" + methodName + "\" cost", ((new Date()).getTime() - executingStartTime) / 1000, "seconds.");

			var containError:Boolean=false;

			if (!event || !event.result || !ErrorHandleUtil.checkErrorForRemoteObject(event.result))
			{
				containError=true;
			}

			if (!containError)
			{
				result(event);
			}

			finallyExecute(event);
		}

		/*
		 * This method is to call a remote function on service
		 */
		protected function sendRemoteCall(methodName:String, ... args):void
		{
			if (TRACE_EXCUTING_TIME)
				this.executingStartTime=(new Date()).getTime();

			this.methodName=methodName;

			var service:Object=ServiceLocator.getInstance().getRemoteObject(serviceId);
			var token:AsyncToken=service.getOperation(methodName).send.apply(null, args);
			token.resultHandler=resultHandler;
			token.faultHandler=faultHandler;
		}
	}
}
