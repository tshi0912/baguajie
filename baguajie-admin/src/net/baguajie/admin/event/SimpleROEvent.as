package net.baguajie.admin.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import flash.events.Event;
	
	import net.baguajie.admin.controller.SimpleROToken;

	public class SimpleROEvent extends CairngormEvent
	{
		public static const SIMPLE_RO_EVENT:String = "simpleROEvent";
		
		public static function newSimpleROEvent(methodName:String, args:Array,
				token:SimpleROToken):SimpleROEvent
		{
			var event:SimpleROEvent = new SimpleROEvent(SIMPLE_RO_EVENT);
			event._methodName = methodName;
			event._args = args;
			event._token = token;
			return event;
		}
		
		protected var _methodName:String;
		
		public function get methodName():String
		{
			return _methodName;
		}
		
		protected var _args:Array;
		
		public function get args():Array
		{
			return _args;
		}
		
		protected var _token:SimpleROToken;
		
		public function get token():SimpleROToken
		{
			return _token;
		}
		
		public function SimpleROEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event
		{
			var newEvent:SimpleROEvent = new SimpleROEvent(type, bubbles, cancelable);
			newEvent._methodName = methodName;
			newEvent._args = args;
			newEvent._token = token;
			return newEvent;
		}
	}
}