package net.baguajie.admin.controller
{
	import mx.collections.ArrayCollection;
	
	public class MultipleROInvoker extends MultipleROInvokerBase
	{
		
		private static const SAVE_OR_UPDATE_PROXY_LIMITS:String = "saveOrUpdateProxyLimits";
		private static const DELETE_PORXY_LIMITS:String="deleteProxyLimits";
		
		public function MultipleROInvoker()
		{
			super();
		}
		
		public function saveOrUpdateProxyLimits(proxyLimits:ArrayCollection):SimpleROToken
		{
			return newInitialRO(SAVE_OR_UPDATE_PROXY_LIMITS, arguments);
		}
		
		public function deleteProxyLimits(proxyLimits:ArrayCollection):SimpleROToken
		{
			return newInitialRO(DELETE_PORXY_LIMITS, arguments);
		}
	}
}