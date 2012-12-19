package net.baguajie.admin.vo
{
	[Bindable]
	[RemoteClass(alias="net.baguajie.domains.User")]
	public class UserVo
	{
		public static const VALID:String = "VALID";
		public static const INVALID:String = "INVALID";
		
		public var id:String;
		public var name:String;
		public var email:String;
		public var gender:String;
		public var status:String;
		public var createdAt:Date;
	}
}