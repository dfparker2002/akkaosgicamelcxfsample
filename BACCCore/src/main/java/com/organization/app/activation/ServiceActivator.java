/**
 * 
 */
package com.organization.app.activation;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.osgi.ActorSystemActivator;

/**
 * @author Ramakrishna Sharvirala
 *
 */
public class ServiceActivator extends ActorSystemActivator {
	
	/*
	public void start(BundleContext context) throws Exception {
		 System.out.println("Starting the bundle of BACC Core");
		
	}

	public void stop(BundleContext context) throws Exception {
		 System.out.println("Stopping the bundle of BACC Core");
		
	}
	*/
	
	private LoggingAdapter log;
	private List<ServiceRegistration> services = new ArrayList<ServiceRegistration>();
	
	@Override
	public void configure(BundleContext context, ActorSystem system) {
		System.out.println("Configuring BACC Core Bundle");
		log = Logging.getLogger(system, this);
		
		
		services.add(context.registerService(ActorSystem.class, system, null));
		log.info("OACC Core bundle is configured");
		//context.re
	}
	
	@Override
	public String getActorSystemName(BundleContext context) {
		//return super.getActorSystemName(context);
		return "BACCActorSystem";
	}
	
	@Override
	public void start(BundleContext context) {
		System.out.println("Starting the bundle of BACC Core");
		//System.out.println("Akka Osgi Core is started");
		super.start(context);
	}
	
	@Override
	public void stop(BundleContext context) {
		System.out.println("Stopping the bundle of BACC Core");
		for(ServiceRegistration service: services) {
			service.unregister();
		}
		super.stop(context);
	}
}
