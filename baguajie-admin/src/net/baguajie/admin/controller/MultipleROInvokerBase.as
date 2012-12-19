package net.baguajie.admin.controller
{
	import com.adobe.cairngorm.control.CairngormEventDispatcher;
	
	import flash.events.EventDispatcher;
	
	import net.baguajie.admin.controller.MultipleROToken;
	import net.baguajie.admin.event.MultipleROEvent;
	
	[Event(name="multipleROComplete", type="com.statestr.gmrm.event.MultipleROEvent")]
	
	public class MultipleROInvokerBase extends EventDispatcher
	{
		protected var counter:int = 0;
		
		protected function newInitialRO(methodName:String, arguments:Array):SimpleROToken
		{
			counter--;
			var token:MultipleROToken = new MultipleROToken();
			token._defaultResultHandler = multipleRODefaultResultHandler;
			var event:MultipleROToken = MultipleROEvent.newInitialROEvent(methodName, arguments, token);
			CairngormEventDispatcher.getInstance().dispatchEvent(event);
			return token;
		}
		
		protected function multipleRODefaultResultHandler():void
		{
			counter++;
			if (counter == 0){
				dispatchEvent(new MultipleROEvent(MultipleROEvent.MULTIPLE_RO_COMPLETE));
			}
		}
	}
}