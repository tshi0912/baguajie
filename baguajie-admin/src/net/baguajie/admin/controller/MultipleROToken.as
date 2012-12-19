package net.baguajie.admin.controller
{
	public class MultipleROToken extends SimpleROToken
	{		
		internal var _defaultResultHandler:Function;
		
		public function get defaultResultHandler():Function
		{
			return _defaultResultHandler;
		}
	}
}