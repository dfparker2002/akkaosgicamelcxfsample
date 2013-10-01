/**
 * 
 */
package com.organization.app.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author Ramakrishna Sharvirala
 *
 */
public class BACCNormalActor extends UntypedActor {
	
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("Received Message  : " + message.toString());
	}
}
