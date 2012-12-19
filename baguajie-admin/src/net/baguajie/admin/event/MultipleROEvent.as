package net.baguajie.admin.event
{
	import flash.events.Event;
	
	import net.baguajie.admin.controller.MultipleROToken;

	public class MultipleROEvent extends SimpleROEvent
	{
		public static const MULTIPLE_RO_EVENT:String = "multipleROEvent";
		
		public static const MULTIPLE_RO_COMPLETE:String = "multipleROComplete";
		
		public static function newInitialROEvent(methodName:String, args:Array,
				token:MultipleROToken):MultipleROEvent
		{
			var event:MultipleROEvent = new MultipleROEvent(MULTIPLE_RO_EVENT);
			event._methodName = methodName;
			event._args = args;
			event._token = token;
			return event;
		}
		
		public function MultipleROEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event
		{
			var newEvent:MultipleROEvent = new MultipleROEvent(type, bubbles, cancelable);
			newEvent._methodName = methodName;
			newEvent._args = args;
			newEvent._token = token;
			return newEvent;
		}
	}
}