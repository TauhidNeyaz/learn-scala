//> using scala "2.13.12"
//> using dep "com.typesafe.akka:akka-actor_2.13:2.8.8"

import akka.actor.{Actor, ActorSystem, Cancellable, Props}

import scala.concurrent.duration._
import scala.language.postfixOps

class SimpleActor extends Actor {
  override def receive: Receive = {
    case message => println(message)
  }
}

val system = ActorSystem("simpleActor")
val simpleActor = system.actorOf(Props[SimpleActor], "scheduler")

system.log.info("Scheduler reminder ...")
import system.dispatcher

system.scheduler.scheduleOnce(1 second) {
  simpleActor ! "reminder"
}

val routine : Cancellable = system.scheduler.schedule(1 second, 2 second) {
  simpleActor ! "heartbeat"
}

system.scheduler.scheduleOnce(5 second) {
  routine.cancel()
}