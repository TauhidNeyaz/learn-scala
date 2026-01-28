//> using scala "2.13.12"
//> using dep "com.typesafe.akka:akka-actor_2.13:2.8.8"

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinPool, RoundRobinRoutingLogic, Router}

/**
 * #1 - Manual Way
 */

class Master extends Actor {
  // step 1 - create Routes
  private val slaves = for (i <- 1 to 5) yield {
    val slave = context.actorOf(Props[Slave], s"slave_$i")
    context.watch(slave)
    ActorRefRoutee(slave)
  }

  // step 2 - define router
  private val router = Router(RoundRobinRoutingLogic(), slaves)

  override def receive: Receive = {
    // step 3 - route the message
    case message =>
      router.route(message, sender())

    // step 4 - handle termination / lifecycle of routes
    case Terminated(ref) =>
      router.removeRoutee(ref)
      var newSlave = context.actorOf(Props[Slave])
      context.watch(newSlave)
      router.addRoutee(newSlave)
  }

}

class Slave extends Actor with ActorLogging {
  override def receive: Receive = {
    case message => log.info(message.toString)
  }
}

val system = ActorSystem("RouterDemo")
val master = system.actorOf(Props[Master], "master")

//for (i <- 1 to 10) {
//  master ! s"[$i] Hello from world ..."
//}

/**
 * #2 - A router actor with its on children
 * called POOL router
 */

val routerPool = system.actorOf(RoundRobinPool(5).props(Props[Slave]), "simplePoolMaster")
for (i <- 1 to 10) {
  routerPool ! s"[$i] Hello from router pool"
}
