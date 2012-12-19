package net.baguajie.admin.controller
{
	import com.adobe.cairngorm.control.CairngormEventDispatcher;
	
	import mx.collections.ArrayCollection;
	
	import net.baguajie.admin.event.SimpleROEvent;

	public class SimpleROInvoker
	{
		private static const GET_SPOTS_AT_PAGE:String="getSpotsAtPage";
		private static const GET_USERS_AT_PAGE:String="getUsersAtPage";
		private static const GET_SPOST_BY_ID:String="getSpotById";
		private static const UPDATE_SPOT_STATUS:String = "updateSpotStatus";
		private static const UPDATE_USER_STATUS:String = "updateUserStatus";

		public static function getSpotsAtPage(page:int):SimpleROToken
		{
			return newSimpleRO(GET_SPOTS_AT_PAGE, arguments);
		}
		
		public static function updateSpotStatus(id:String, status:String):SimpleROToken
		{
			return newSimpleRO(UPDATE_SPOT_STATUS, arguments);
		}
		
		public static function getUsersAtPage(page:int):SimpleROToken
		{
			return newSimpleRO(GET_USERS_AT_PAGE, arguments);
		}
		
		public static function updateUserStatus(id:String, status:String):SimpleROToken
		{
			return newSimpleRO(UPDATE_USER_STATUS, arguments);
		}

		private static function newSimpleRO(methodName:String, arguments:Array):SimpleROToken
		{
			var token:SimpleROToken=new SimpleROToken();
			var event:SimpleROEvent=SimpleROEvent.newSimpleROEvent(methodName, arguments, token);
			CairngormEventDispatcher.getInstance().dispatchEvent(event);
			return token;
		}

		public function SimpleROInvoker()
		{
		}
	}
}
