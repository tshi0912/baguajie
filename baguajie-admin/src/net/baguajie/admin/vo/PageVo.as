package net.baguajie.admin.vo
{
	import mx.collections.ArrayCollection;

	[Bindable]
	[RemoteClass(alias="net.baguajie.vo.PageVo")]
	public class PageVo
	{
		public var number:int;
		public var size:int;
		public var totalPages:int;
		public var content:ArrayCollection;
	}
}