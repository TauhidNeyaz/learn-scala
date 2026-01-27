//> using scala "2.13.12"
//> using dep "com.typesafe.akka:akka-actor_2.13:2.8.8"

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.event.Logging

// 1. Explicit Logging

class ActorExplicitLogging extends Actor {
  val logger = Logging(context.system, this)

  /**
   * types of log
   *  - INFO
   *  - WARNING / WARN
   *  - DEBUG
   *  - ERROR
   *
   */

  override def receive: Receive = {
    case message => logger.info(message.toString)
  }
}

val actor = ActorSystem("actor")
val exLogger = actor.actorOf(Props[ActorExplicitLogging], "ExplicitLogging")

exLogger ! "Simple Log"


// 2. Actor Logging

class ActorWithLogging extends Actor with ActorLogging {
  override def receive: Receive = {
    case message => log.info(message.toString)
  }
}

val actorLogger = actor.actorOf(Props[ActorWithLogging], "ActorLogging")
actorLogger ! "Simple Log by Actor"