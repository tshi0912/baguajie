package net.baguajie.admin.vo
{
	[Bindable]
	[RemoteClass(alias="net.baguajie.domains.Comment")]
	public class CommentVo
	{
		public static const VALID:String = "VALID";
		public static const INVALID:String = "INVALID";
		
		public var id:String;
		public var content:String;
		public var createdBy:UserVo;
		public var createdAt:Date;
		public var status:String;
	}
}