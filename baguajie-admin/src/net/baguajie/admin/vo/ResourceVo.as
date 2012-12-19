package net.baguajie.admin.vo
{
	[Bindable]
	[RemoteClass(alias="net.baguajie.domains.Resource")]
	public class ResourceVo
	{
		public var id:String;
		public var resId:String;
		public var ext:String;
		public var orgSize:Array;
	}
}