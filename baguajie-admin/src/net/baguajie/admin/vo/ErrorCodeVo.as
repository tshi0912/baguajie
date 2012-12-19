package net.baguajie.admin.vo
{	
	[Bindable]
	[RemoteClass(alias="net.baguajie.web.ros.ErrorCode")]
	public class ErrorCodeVo
	{
		public var code:String;
		
		public var severity:int;
		
		public var message:String;
		
		public var description:String;
	}
}