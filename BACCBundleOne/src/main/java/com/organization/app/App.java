package com.organization.app;

import com.organization.app.actors.BACCCamelCXFRestActor;
import com.organization.app.actors.BACCNormalActor;

import akka.actor.ActorSystem;
import akka.actor.Props;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
            
        final ActorSystem system = ActorSystem.create("BACCActorSystem");
        system.actorOf(Props.create(BACCCamelCXFRestActor.class));
        system.actorOf(Props.create(BACCNormalActor.class));
            
        
    }
}
