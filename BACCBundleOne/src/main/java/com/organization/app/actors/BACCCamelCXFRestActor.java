/**
 * 
 */
package com.organization.app.actors;


import javax.ws.rs.core.Response;

import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author Ramakrishna Sharvirala
 *
 */
public class BACCCamelCXFRestActor extends UntypedConsumerActor {
	
	/**
	 * AKKA logging adapter to do asynchronous logging.
	 */
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public String getEndpointUri() {
		return "cxfrs:http://127.0.0.1:7070/test/services/rest/bacc/?bindingStyle=SimpleConsumer&resourceClasses=com.organization.app.beans.BACCRestService";
		//return "cxfrs:http://127.0.0.1:7070/test/services/rest/bacc/info?bindingStyle=Default";
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof CamelMessage) {
			CamelMessage camelMessage = (CamelMessage) message;

			String body = camelMessage.getBodyAs(String.class, getCamelContext());
			log.info("Received Message : {} ", body);
			
			System.out.println(body);

			sender().tell(new CamelMessage(Response.ok().entity("Test Response").build(), camelMessage.getHeaders()), getSelf());
			//sender().tell(new CamelMessage("EmptyResponse", camelMessage.getHeaders()), getSelf());
		}	else {
			unhandled(message);
		}
		
	}
	
	/**
	 * No auto ack!
	 */
	@Override
	public boolean autoAck() {
		return false;
	}
}
