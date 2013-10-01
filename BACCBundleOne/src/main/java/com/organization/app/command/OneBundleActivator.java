/**
 * 
 */
package com.organization.app.command;

import java.util.concurrent.TimeUnit;

import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import scala.concurrent.duration.Duration;
import scala.concurrent.Future;

import com.organization.app.actors.BACCCamelCXFRestActor;
import com.organization.app.actors.BACCNormalActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.dispatch.OnComplete;
import akka.util.Timeout;



/**
 * @author Ramakrishna Sharvirala
 *
 */
public class OneBundleActivator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		System.out.println("Starting the bundle of BACC BundleOne");
		
		
		ServiceTracker serviceTracker = new ServiceTracker(context, ActorSystem.class.getName(), null);
		
		serviceTracker.open();
		ActorSystem system = (ActorSystem)serviceTracker.getService();
		
		System.out.println(system.name() + "----" + system.isTerminated());
		System.out.println(context.getBundle().getSymbolicName() + ":" + context.getBundle().getBundleId());
		
		//1. Normal Actor Creation
		ActorRef normalActorRef = system.actorOf(Props.create(BACCNormalActor.class), "BACCNormalActor");
		System.out.println(normalActorRef != null ? normalActorRef.path() : "ActorRef is null");
		
		Camel camel =  CamelExtension.get(system);
		camel.context().setTracing(true);
		//camel.context().addComponent("cxfrs", new CxfRsComponent(camel.context()));
		//camel.context().setDebugger();
		Timeout timeout = new Timeout(Duration.create(10, TimeUnit.SECONDS));
		
		Future<ActorRef> activationFuture = camel.activationFutureFor(normalActorRef, timeout, system.dispatcher());
		activationFuture.onComplete(new OnComplete<ActorRef>() {
			@Override
			public void onComplete(Throwable throwable, ActorRef actorRef)
					throws Throwable {
				if(throwable != null) {
					System.out.println("The below is error thrown from actor creation");
					throwable.printStackTrace();
				} else {
					System.out.println("Actor is Successfully Created");
				}
			}
		}, system.dispatcher());
		
		
		camel.context().addComponent("cxfrs", new CxfRsComponent(camel.context()));
		
		//2. Camel CXF RS Based Actor Creation
		ActorRef camelBasedActorRef = system.actorOf(Props.create(BACCCamelCXFRestActor.class), "BACCCamelCXFRestActor");
		
		System.out.println(camelBasedActorRef != null ? camelBasedActorRef.path() : "ActorRef is null");
		
		camel.context().setTracing(true);
		
		
		activationFuture = camel.activationFutureFor(camelBasedActorRef, timeout, system.dispatcher());
		activationFuture.onComplete(new OnComplete<ActorRef>() {
			@Override
			public void onComplete(Throwable throwable, ActorRef actorRef)
					throws Throwable {
				if(throwable != null) {
					System.out.println("The below is error thrown from actor creation");
					throwable.printStackTrace();
				} else {
					System.out.println("Actor is Successfully Created");
				}
			}
		}, system.dispatcher());
		
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping the bundle of BACC BundleOne");
		
	}

}
