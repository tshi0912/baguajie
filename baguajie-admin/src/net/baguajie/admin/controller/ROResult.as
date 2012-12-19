package net.baguajie.admin.controller
{	
	import mx.collections.ArrayCollection;
	
	import net.baguajie.admin.vo.ErrorCodeVo;

	[RemoteClass(alias="net.baguajie.web.ros.ROResult")]
	public class ROResult
	{
		public var errors:ArrayCollection;
		
		public var result:Object;
		
		public var errorCode:ErrorCodeVo;
	}
}