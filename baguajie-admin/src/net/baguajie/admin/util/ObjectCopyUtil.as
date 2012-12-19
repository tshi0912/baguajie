package net.baguajie.admin.util
{
	import mx.collections.IList;
	import mx.utils.ObjectUtil;
	
	public class ObjectCopyUtil
	{
		/**
		 * Copy the values from the source obj into the destination obj. They must be the save type!
		 */
		private static const copyPropsOption:Object = {includeReadOnly: false, includeTransient: false};
		private static var classProperties:Array;
		private static var classField:Object;
		public static function copyProperties(source:Object, destination:Object):void
		{
			classProperties = ObjectUtil.getClassInfo(source, null, copyPropsOption).properties;
			for each (classField in classProperties)
			{
				destination[classField] = source[classField];
			}
		}
		
		public static function mergeUpdateArray(updateList:IList, baseArray:Array, keyProperty:Object):void
		{
			if(!updateList)
				return;
			updateList = IList(ObjectUtil.copy(updateList));
			for (var i:int = baseArray.length - 1; i >= 0; i--)
			{
				var baseItem:Object = baseArray[i];
				for (var j:int = updateList.length - 1; j >= 0; j--)
				{
					var updateItem:Object = updateList.getItemAt(j);
					if (baseItem[keyProperty] === updateItem[keyProperty])
					{
						//						baseList.setItemAt(updateItem, i);
						copyProperties(updateItem, baseItem);
						updateList.removeItemAt(j);
						break;
					}
				}
			}
			
			if (updateList.length > 0)
			{
				var n:int = updateList.length;
				for (var k:int = 0; k < n; k++)
				{
					baseArray.push(updateList.getItemAt(k));
				}
			}
		}
		
		public static function mergeUpdateList(updateList:IList, baseList:IList, keyProperty:Object):void
		{
			if(!updateList)
				return;
			updateList = IList(ObjectUtil.copy(updateList));
			for (var i:int = baseList.length - 1; i >= 0; i--)
			{
				var baseItem:Object = baseList.getItemAt(i);
				for (var j:int = updateList.length - 1; j >= 0; j--)
				{
					var updateItem:Object = updateList.getItemAt(j);
					if (baseItem[keyProperty] === updateItem[keyProperty])
					{
						copyProperties(updateItem, baseItem);
						updateList.removeItemAt(j);
						break;
					}
				}
			}
			
			if (updateList.length > 0)
			{
				var n:int = updateList.length;
				for (var k:int = 0; k < n; k++)
				{
					baseList.addItem(updateList.getItemAt(k));
				}
			}
		}
		
		public static function mergeRemoveList(toBeRemovedList:IList, baseList:IList, keyProperty:Object):void
		{
			if(!toBeRemovedList)
				return;
			toBeRemovedList = IList(ObjectUtil.copy(toBeRemovedList));
			for(var i:int = baseList.length - 1; i>=0; i--)
			{
				var baseItem:Object = baseList.getItemAt(i);
				for (var j:int = toBeRemovedList.length - 1; j >= 0; j--)
				{
					var toBeRemovedItem:Object = toBeRemovedList.getItemAt(j);
					if (baseItem[keyProperty] === toBeRemovedItem[keyProperty])
					{
						baseList.removeItemAt(i);
						toBeRemovedList.removeItemAt(j);
						break;
					}
				}
			}
		}
		
		public static function buildAvailableList(existedList:IList, baseList:IList, keyProperty:Object):void
		{
			existedList = IList(ObjectUtil.copy(existedList));
			for(var i:int = baseList.length -1; i>=0; i--)
			{
				var baseItem:Object = baseList.getItemAt(i);	
				for(var j:int = existedList.length - 1; j >=0; j--)
				{
					var existedItem:Object = existedList.getItemAt(j);
					if (baseItem[keyProperty] === existedItem[keyProperty])
					{
						baseList.removeItemAt(i);
						existedList.removeItemAt(j);
						break;
					}	
				}
			}
			
		}
		
		public static function buildAvailableListWithDiffKey(existedList:IList, baseList:IList, 
			leftKeyProperty:Object, rightKeyProperty:Object):void
		{
			existedList = IList(ObjectUtil.copy(existedList));
			for(var i:int = baseList.length -1; i>=0; i--)
			{
				var baseItem:Object = baseList.getItemAt(i);	
				for(var j:int = existedList.length - 1; j >=0; j--)
				{
					var existedItem:Object = existedList.getItemAt(j);
					if (baseItem[rightKeyProperty] === existedItem[leftKeyProperty])
					{
						baseList.removeItemAt(i);
						existedList.removeItemAt(j);
						break;
					}	
				}
			}
			
		}
		
		public static function getItemByProperty(list:IList, propertyName:String, propertyValue:Object):Object
		{
			var item:Object = null;
			if(!propertyName && !propertyValue){
				return item;
			}
			for (var i:int = list.length -1; i>=0; i--)
			{
				var tempItem:Object = list.getItemAt(i);
				if(tempItem[propertyName] == propertyValue)
				{
					item = tempItem;
					break;
				}
			}
				
			return item;
		}
		
		public static function getItemIndexByProperty(list:IList, item:Object, propertyName:Object=null, propertyOwning:String="all"):int
		{
			var index:int = -1;			
			if ( item == null ){
				return index;
			}
			for (var i:int = list.length -1; i>=0; i--){
				var tempItem:Object = list.getItemAt(i);
				if(propertyName && propertyOwning=="all"){
					if(tempItem[propertyName] == item[propertyName]){
						index = i;
						break;
					}
				}else if (propertyName && propertyOwning=="left"){
					if(tempItem[propertyName] == item){
						index = i;
						break;
					}
				}else if (propertyName && propertyOwning=="right"){
					if(tempItem == item[propertyName]){
						index = i;
						break;
					}
				}else if (!propertyName){
					if(tempItem == item){
						index = i;
						break;
					}
				}
			}
				
			return index;
		}
		
		public static function getStrItemIndexByProperty(list:IList, item:Object, propertyName:Object=null, caseSensitive:Boolean=true, propertyOwning:String="all"):int
		{
			var index:int = -1;
			if(!item || !(item is String)){
				return index;
			}
			for (var i:int = list.length -1; i>=0; i--){
				var tempItem:Object = list.getItemAt(i);
				if(!propertyName && !(tempItem is String)){
					continue;
				}else if (propertyName && !(tempItem[propertyName] is String)){
					continue;
				}
				if(propertyName && propertyOwning=="all"){
					if(caseSensitive){
						if(tempItem[propertyName] == item[propertyName]){
							index = i;
							break;
						}
					}else{
						if(String(tempItem[propertyName]).toUpperCase() == 
						   String(item[propertyName]).toUpperCase()){
							index = i;
							break;
						}
					}
				}else if (propertyName && propertyOwning=="left"){
					if(caseSensitive){
						if(tempItem[propertyName] == item){
							index = i;
							break;
						}
					}else{
						if(String(tempItem[propertyName]).toUpperCase() == 
						   String(item).toUpperCase()){
							index = i;
							break;
						}
					}
				}else if (propertyName && propertyOwning=="right"){
					if(caseSensitive){
						if(tempItem == item[propertyName]){
							index = i;
							break;
						}
					}else{
						if(String(tempItem).toUpperCase() == 
						   String(item[propertyName]).toUpperCase()){
							index = i;
							break;
						}
					}
				}else if (!propertyName){
					if(caseSensitive){
						if(tempItem == item){
							index = i;
							break;
						}
					}else{
						if(String(tempItem).toUpperCase() == 
						   String(item).toUpperCase()){
							index = i;
							break;
						}
					}
				}
			}
				
			return index;
		}
	}
}