/**
 * 
 */
package com.organization.app.beans;

import java.util.concurrent.TimeUnit;

import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.camel.Camel;
import akka.camel.CamelExtension;
import akka.dispatch.OnComplete;
import akka.util.Timeout;

import com.organization.app.actors.BACCCamelCXFRestActor;
import com.organization.app.actors.BACCNormalActor;

/**
 * @author Ramakrishna Sharvirala
 * 
 */
public class ActorSystemBean {

	private ActorRefFactory actorRefFactory;

	/**
	 * @return the actorRefFactory
	 */
	public ActorRefFactory getActorRefFactory() {
		return actorRefFactory;
	}

	/**
	 * @param actorRefFactory
	 *            the actorRefFactory to set
	 */
	public void setActorRefFactory(ActorRefFactory actorRefFactory) {
		this.actorRefFactory = actorRefFactory;
		try {
			System.out.println("Bean is getting intialized... !");
			ActorSystem actorSystem = (ActorSystem) actorRefFactory;

			System.out.println("Bean is getting intialized... !");
			// 1. Normal Actor Creation
			ActorRef normalActorRef = actorSystem.actorOf(
					Props.create(BACCNormalActor.class), "OACCNormalActor");
			System.out.println(normalActorRef != null ? normalActorRef.path()
					: "ActorRef is null");

			Camel camel = CamelExtension.get(actorSystem);
			camel.context().setTracing(true);
			// camel.context().setDebugger();
			Timeout timeout = new Timeout(Duration.create(10, TimeUnit.SECONDS));

			Future<ActorRef> activationFuture = camel.activationFutureFor(
					normalActorRef, timeout, actorSystem.dispatcher());
			activationFuture.onComplete(new OnComplete<ActorRef>() {
				@Override
				public void onComplete(Throwable throwable, ActorRef actorRef)
						throws Throwable {
					if (throwable != null) {
						System.out
								.println("The below is error thrown from actor creation");
						throwable.printStackTrace();
					} else {
						System.out.println("Actor is Successfully Created");
					}
				}
			}, actorSystem.dispatcher());

			// 2. Camel CXF RS Based Actor Creation
			ActorRef camelBasedActorRef = actorSystem.actorOf(
					Props.create(BACCCamelCXFRestActor.class),
					"OACCCamelCXFRestActor");

			System.out.println(camelBasedActorRef != null ? camelBasedActorRef
					.path() : "ActorRef is null");

			camel.context().setTracing(true);

			activationFuture = camel.activationFutureFor(camelBasedActorRef,
					timeout, actorSystem.dispatcher());
			activationFuture.onComplete(new OnComplete<ActorRef>() {
				@Override
				public void onComplete(Throwable throwable, ActorRef actorRef)
						throws Throwable {
					if (throwable != null) {
						System.out
								.println("The below is error thrown from actor creation");
						throwable.printStackTrace();
					} else {
						System.out.println("Actor is Successfully Created");
					}
				}
			}, actorSystem.dispatcher());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
