package net.baguajie.admin.vo
{
	[Bindable]
	[RemoteClass(alias="net.baguajie.domains.User")]
	public class UserVo
	{
		public static const VALID:String = "VALID";
		public static const INVALID:String = "INVALID";
		public static const USER:String = "USER";
		public static const ADMIN:String = "ADMIN";
		
		public var id:String;
		public var name:String;
		public var email:String;
		public var gender:String;
		public var status:String;
		public var avatar:ResourceVo;
		public var role:String;
		public var createdAt:Date;
	}
}