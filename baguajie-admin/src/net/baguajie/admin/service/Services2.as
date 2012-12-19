package net.baguajie.admin.service
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	public class Services2 extends ServiceLocator
	{
		public var adminRO:RemoteObject;
		
		public function Services2()
		{
			super();
			adminRO = new RemoteObject();
			adminRO.destination = "adminRO";
			adminRO.showBusyCursor = true;
			adminRO.addEventListener( ResultEvent.RESULT, resultHandler);
			adminRO.addEventListener( FaultEvent.FAULT, faultHandler);
			var cs:ChannelSet = new ChannelSet();
			var amfChannel:AMFChannel = new AMFChannel("my-amf",
				"http://localhost:8080/baguajie-web/messagebroker/amf");
			cs.addChannel(amfChannel);
			adminRO.channelSet = cs;
		}
		
		private function resultHandler(event:ResultEvent):void{
			event.token.resultHandler(event);
		}
		
		private function faultHandler(event:FaultEvent):void{
			event.token.faultHandler(event);
		}
	}
}