package net.baguajie.admin.vo
{
	[Bindable]
	[RemoteObject(alias="org.springframework.data.domain.PageImpl")]
	public class PageVo
	{
		public var number:int;
		
		public var size:int;
		
		public var totalPages:int;
		
		public var content:Object;
	}
}