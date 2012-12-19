package net.baguajie.admin.vo
{
	[Bindable]
	[RemoteClass(alias="net.baguajie.domains.Spot")]
	public class SpotVo
	{
		public static const VALID:String = "VALID";
		public static const INVALID:String = "INVALID";
		
		public var id:String;
		public var image:ResourceVo;
		public var name:String;
		public var summary:String;
		public var lngLat:Array;
		public var createdAt:Date;
		public var createdBy:UserVo;
		public var updatedAt:UserVo;
		public var city:String;
		public var category:String;
		public var trackedCount:int;
		public var forwardedCount:int;
		public var commentedCount:int;
		public var sharedCount:int;
		public var status:String;
	}
}