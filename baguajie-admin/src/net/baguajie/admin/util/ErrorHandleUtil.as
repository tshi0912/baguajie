package net.baguajie.admin.util
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.utils.ObjectUtil;
	
	import net.baguajie.admin.controller.ROResult;
	
	public class ErrorHandleUtil {
		public static const FATAL_INT_ERROR:String = "FATAL_INT_ERROR";
		
		public static function checkErrorForRemoteObject(result:Object) : Boolean {
			var value:Object = {};
			if (result == null) {
				return false;
			}
			var resultRO:ROResult;
			try {
				if (result is ROResult) {
					resultRO = ROResult(result);
					return checkROResult(resultRO);
				}
				else {
					return false;
				}
			} catch (error:Error) {
				Alert.show(error.message, error.name);
				return false;
			}
			return true;
		}
		
		public static function checkROResult(resultRO:ROResult) : Boolean {
			if(resultRO.errorCode) {
				return false;
			}
			return true;
		}
		
	}
}